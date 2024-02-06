package cn.gdrfgdrf.ConnectComputerServer.Netty.Handler;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Exception.IllegalParameterNettyException;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.Protobuf.Connection.Heart.HeartProto;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author gdrfgdrf
 */
public class HeartPingPongHandler extends ChannelInboundHandlerAdapter {
    private int lossConnectCount = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent event) {
            switch (event.state()) {
                case READER_IDLE -> {
                    lossConnectCount++;

                    if (lossConnectCount >= 5) {
                        throw new IllegalParameterNettyException(
                                ResultEnum.NETTY_HEART_TIMEOUT,
                                HeartProto.HeartTimeoutPacket.class,
                                null,
                                true
                        );
                    }
                }
                case WRITER_IDLE -> {
                    HeartProto.HeartPingPacket heartPingPacket = HeartProto.HeartPingPacket.newBuilder()
                            .build();

                    Writer.write(
                            ctx.channel(),
                            heartPingPacket
                    );
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        lossConnectCount = 0;
        super.channelRead(ctx, msg);
    }


}
