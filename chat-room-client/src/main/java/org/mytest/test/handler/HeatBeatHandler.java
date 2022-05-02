package org.mytest.test.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.message.impl.PingMessage;

/**
 * @author gemo
 * @date 2022/5/2 21:30
 **/
@Slf4j
public class HeatBeatHandler extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent ise = (IdleStateEvent) evt;
            if (ise.state() == IdleState.WRITER_IDLE) {
                ctx.channel().writeAndFlush(new PingMessage());
            }
        }
    }
}
