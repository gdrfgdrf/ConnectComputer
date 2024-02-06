package cn.gdrfgdrf.ConnectComputerServer.Netty.Handler;

import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Holder.SpringContextHolder;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.PassNettyHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Base.PacketHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.NettyUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Exception.IllegalParameterNettyException;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Utils.MessageTypeUnpacker;
import cn.gdrfgdrf.Protobuf.Action.Account.AccountErrorProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Common.ExternalErrorProto;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IUserEntityService;
import cn.gdrfgdrf.ConnectComputerServer.Utils.TokenUtils;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.util.Map;

/**
 * @author gdrfgdrf
 */
public class AuthenticateHandler extends ChannelInboundHandlerAdapter {
    private final IUserEntityService userEntityService = SpringContextHolder.getBean(IUserEntityService.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws InvalidProtocolBufferException {
        if (!(obj instanceof BaseProto.Packet packet)) {
            ctx.fireChannelRead(obj);
            return;
        }
        if (passHandler(packet)) {
            ctx.fireChannelRead(packet);
            return;
        }
        if (!NettyServer.getInstance().containsUser(ctx.channel())) {
            ctx.fireChannelRead(packet);
            return;
        }

        NettyUser nettyUser = NettyServer.getInstance().getUser(ctx.channel());
        String token = nettyUser.getToken();

        try {
            TokenUtils.verifyToken(token);
        } catch (ExpiredJwtException e) {
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_TOKEN_EXPIRED,
                    AccountErrorProto.TokenExpiredPacket.class,
                    packet.getRequestId(),
                    true
            );
        } catch (UnsupportedJwtException |
                 MalformedJwtException |
                 SignatureException |
                 IllegalArgumentException e) {
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_TOKEN_INVALID,
                    AccountErrorProto.TokenInvalidPacket.class,
                    packet.getRequestId(),
                    true
            );
        }

        Map<String, Object> map = TokenUtils.parseToken(token);
        Integer id = Integer.parseInt(map.get("id").toString());
        UserEntity user = userEntityService.getById(id);

        if (user == null) {
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_NOT_FOUND_USER,
                    ExternalErrorProto.NotFoundUserPacket.class,
                    packet.getRequestId(),
                    true
            );
        }
        if (user.getTokenEntities().isEmpty()) {
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_TOKEN_INVALID,
                    AccountErrorProto.TokenInvalidPacket.class,
                    packet.getRequestId(),
                    true
            );
        }
        if (!user.containsToken(token)) {
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_TOKEN_INVALID,
                    AccountErrorProto.TokenInvalidPacket.class,
                    packet.getRequestId(),
                    true
            );
        }

        ctx.fireChannelRead(packet);
    }

    public boolean passHandler(BaseProto.Packet packet) throws InvalidProtocolBufferException {
        Message result = MessageTypeUnpacker.unpack(packet.getData());
        PacketHandler packetHandler = NettyServer.getInstance().HANDLER.get(result.getClass());
        if (packetHandler == null) {
            return false;
        }

        Class<? extends PacketHandler> clazz = packetHandler.getClass();

        PassNettyHandler passNettyHandler = AnnotatedElementUtils.findMergedAnnotation(clazz, PassNettyHandler.class);
        if (passNettyHandler != null) {
            Class<? extends ChannelHandlerAdapter>[] pass = passNettyHandler.pass();
            return ArrayUtils.contains(pass, AuthenticateHandler.class);
        }

        return false;
    }
}
