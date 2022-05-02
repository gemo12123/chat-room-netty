package org.mytest.test.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.message.impl.GroupChatResponseMessage;
import org.mytest.test.util.ClientUtils;

/**
 * @author gemo
 * @date 2022/5/2 17:30
 **/
@Slf4j
@ChannelHandler.Sharable
public class GroupChatResponseHandler extends SimpleChannelInboundHandler<GroupChatResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatResponseMessage msg) throws Exception {
        boolean success = msg.isSuccess();
        String reason = msg.getReason();
        ClientUtils.printSystemResponse(success, reason);
    }
}
