package cn.gdrfgdrf.ConnectComputerServer.Netty.Handler;

import cn.gdrfgdrf.ConnectComputerServer.Holder.SpringContextHolder;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Condition;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Base.PacketHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.NettyUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Enum.ConditionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Exception.IllegalParameterNettyException;
import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Exception.ExceptionDispatchException;
import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.ExceptionDispatcher;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Utils.MessageTypeUnpacker;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Common.ExternalErrorProto;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.Protobuf.Common.InternalErrorProto;
import cn.gdrfgdrf.Protobuf.Connection.Goodbye.GoodbyeProto;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IUserEntityService;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Utils.NettyServerUtils;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * @author gdrfgdrf
 */
@SuppressWarnings("unchecked")
@Slf4j
public class DistributeHandler extends ChannelInboundHandlerAdapter {
    private final ExceptionDispatcher exceptionDispatcher = SpringContextHolder.getBean(ExceptionDispatcher.class);
    private final IUserEntityService userEntityService = SpringContextHolder.getBean(IUserEntityService.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        NettyServer.getInstance().addNotLoggedInConnection(ctx.channel(), System.currentTimeMillis());

        log.info(
                "Connected: {}",
                ctx.channel().remoteAddress()
        );
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (!(obj instanceof BaseProto.Packet packet)) {
            ctx.fireChannelRead(obj);
            return;
        }

        Message result = MessageTypeUnpacker.unpack(packet.getData());
        PacketHandler packetHandler = NettyServer.getInstance().HANDLER.get(result.getClass());
        if (packetHandler == null) {
            return;
        }

        Class<? extends PacketHandler> clazz = packetHandler.getClass();
        if (AnnotatedElementUtils.hasAnnotation(clazz, Condition.class)) {
            Condition condition = AnnotatedElementUtils.findMergedAnnotation(clazz, Condition.class);
            assert condition != null;

            for (int i = 0; i < condition.condition().length; i++) {
                ConditionEnum conditionEnum = condition.condition()[i];
                ResultEnum resultEnum = condition.notSatisfiedResult()[i];
                boolean need = true;

                if (condition.need().length > i) {
                    need = condition.need()[i];
                }

                if (conditionEnum.getImpl().condition(ctx.channel()) != need) {
                    throw new IllegalParameterNettyException(
                            resultEnum,
                            ExternalErrorProto.ConditionNotSatisfiedPacket.class,
                            packet.getRequestId(),
                            conditionEnum.isClose()
                    );
                }
            }
        }

        packetHandler.handle(ctx.channel(), packet, result);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof IllegalParameterNettyException ||
                cause.getCause() instanceof IllegalParameterNettyException) {
            IllegalParameterNettyException exception;

            if (cause instanceof IllegalParameterNettyException) {
                exception = (IllegalParameterNettyException) cause;
            } else {
                exception = (IllegalParameterNettyException) cause.getCause();
            }

            Writer.write(ctx.channel(), exception);

            if (exception.isClose()) {
                log.info(
                        "Say goodbye to user ({})",
                        ctx.channel().remoteAddress()
                );

                ChannelFuture channelFuture = Writer.write(
                        ctx.channel(),
                        GoodbyeProto.GoodbyePacket.newBuilder()
                                .build(),
                        null,
                        ResultEnum.NETTY_GOODBYE
                );
                if (channelFuture != null) {
                    channelFuture.addListener(listener -> {
                        ctx.channel().close();
                    });
                }
            }
        } else {
            try {
                exceptionDispatcher.dispatch(ctx.channel(), cause);
            } catch (ExceptionDispatchException ignored) {
                cause.printStackTrace();
            }

            Writer.error(
                    ctx.channel(),
                    InternalErrorProto.ErrorPacket.newBuilder().build(),
                    null
            );
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        NettyServerUtils.removeConnection(
                userEntityService,
                ctx.channel(),
                this
        );

        log.info(
                "Disconnected: {}",
                ctx.channel().remoteAddress()
        );
    }
}
