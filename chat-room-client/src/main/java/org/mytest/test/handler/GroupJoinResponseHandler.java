package org.mytest.test.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mytest.test.ChatRoomClient;
import org.mytest.test.message.impl.GroupJoinResponseMessage;
import org.mytest.test.util.ClientUtils;

/**
 * @author gemo
 * @date 2022/5/2 11:05
 **/
public class GroupJoinResponseHandler extends SimpleChannelInboundHandler<GroupJoinResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinResponseMessage msg) throws Exception {
        boolean success = msg.isSuccess();
        String reason = msg.getReason();
        if (success) {
            String joinUsername = msg.getJoinUsername();
            String joinGroupName = msg.getJoinGroupName();
            reason = ChatRoomClient.CLIENT_MANAGER.getCurrentUsername().equals(joinUsername) ?
                    "您已经加入 \"" + joinGroupName + "\" 聊天室！"
                    : "用户 \"" + joinUsername + "\" 已经加入 \"" + joinGroupName + "\" 聊天室！";
        }
        ClientUtils.printSystemResponse(success, reason);
    }
}
