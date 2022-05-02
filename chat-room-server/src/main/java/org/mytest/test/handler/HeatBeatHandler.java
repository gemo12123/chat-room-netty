package org.mytest.test.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.message.impl.PingMessage;

/**
 * @author gemo
 * @date 2022/5/2 19:29
 **/
@Slf4j
@ChannelHandler.Sharable
public class HeatBeatHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent ise = (IdleStateEvent) evt;
            if (ise.state() == IdleState.READER_IDLE) {
                log.info("5s 没有读取消息了！"+ctx.channel());
                ctx.channel().close();
            }
        }
    }
}
