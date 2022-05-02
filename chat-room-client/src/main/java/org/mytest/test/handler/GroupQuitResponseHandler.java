package org.mytest.test.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mytest.test.ChatRoomClient;
import org.mytest.test.message.impl.GroupQuitResponseMessage;
import org.mytest.test.util.ClientUtils;

/**
 * @author gemo
 * @date 2022/5/2 16:41
 **/
public class GroupQuitResponseHandler extends SimpleChannelInboundHandler<GroupQuitResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitResponseMessage msg) throws Exception {
        boolean success = msg.isSuccess();
        String reason = msg.getReason();
        if (success) {
            String username = msg.getUsername();
            String groupName = msg.getGroupName();
            if (ChatRoomClient.CLIENT_MANAGER.getCurrentUsername().equals(username)) {
                reason = "您已退出 \"" + groupName + "\" 聊天室!";
            } else {
                reason = "\"" + username + "\" 已退出 \"" + groupName + "\" 聊天室!";
            }
        }
        ClientUtils.printSystemResponse(success,reason);
    }
}
