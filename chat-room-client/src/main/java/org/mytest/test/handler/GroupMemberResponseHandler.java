package org.mytest.test.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mytest.test.message.impl.GroupMemberResponseMessage;
import org.mytest.test.util.ClientUtils;

import java.util.List;

/**
 * @author gemo
 * @date 2022/5/2 11:49
 **/
public class GroupMemberResponseHandler extends SimpleChannelInboundHandler<GroupMemberResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMemberResponseMessage msg) throws Exception {
        boolean success = msg.isSuccess();
        String reason = msg.getReason();
        if (success) {
            String groupName = msg.getGroupName();
            String groupOwner = msg.getGroupOwner();
            List<String> members = msg.getMembers();
            members.remove(groupOwner);
            members.add(0, groupOwner + "(群主)");
            reason = "\"" + groupName + "\" 聊天室的成员有：\r\n" + String.join(",", members);
        }
        ClientUtils.printSystemResponse(success, reason);
    }
}
