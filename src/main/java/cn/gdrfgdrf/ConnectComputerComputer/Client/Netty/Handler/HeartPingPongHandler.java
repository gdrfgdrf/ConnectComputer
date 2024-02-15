package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Objects;

/**
 * @author gdrfgdrf
 */
public class HeartPingPongHandler extends SimpleChannelInboundHandler<Object> {
    public static int count = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent idleStateEvent) {
            if (Objects.requireNonNull(idleStateEvent.state()) == IdleState.READER_IDLE) {
                count++;

                if (count >= 2) {
                    ctx.channel().close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object obj) throws Exception {
        count = 0;
        ctx.fireChannelRead(obj);
    }
}
