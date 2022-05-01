package org.mytest.test.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mytest.test.message.impl.ChatResponseMessage;
import org.mytest.test.util.ClientUtils;

/**
 * @author gemo
 * @date 2022/5/1 10:43
 **/
@ChannelHandler.Sharable
public class ChatResponseHandler extends SimpleChannelInboundHandler<ChatResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatResponseMessage msg) throws Exception {
        boolean result = msg.isSuccess();
        String content = msg.getContent();
        ClientUtils.printSystemResponse(result,content);
    }
}
