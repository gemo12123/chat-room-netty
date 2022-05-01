package org.mytest.test.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.ChatRoomClient;
import org.mytest.test.message.impl.GroupCreateResponseMessage;
import org.mytest.test.util.ClientUtils;

import java.util.stream.Collectors;

/**
 * @author gemo
 * @date 2022/5/1 18:55
 **/
@Slf4j
@ChannelHandler.Sharable
public class GroupCreateResponseHandler extends SimpleChannelInboundHandler<GroupCreateResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateResponseMessage msg) throws Exception {
        boolean success = msg.isSuccess();
        String groupOwner = msg.getGroupOwner();
        String successStr = null;
        String currentUsername = ChatRoomClient.CLIENT_MANAGER.getCurrentUsername();
        msg.getMembers().remove(currentUsername);
        msg.getMembers().remove(msg.getGroupOwner());
        if (currentUsername.equals(groupOwner)) {
            String s = "您创建了 \"%s\" 聊天室，并邀请了 %s 加入！";
            successStr = String.format(s,
                    msg.getGroupName(),
                    String.join(",", msg.getMembers()));
        } else {
            String s = "您和 %s 被 %s 邀请加入 \"%s\" 聊天室！";
            successStr = String.format(s,
                    String.join(",", msg.getMembers()),
                    groupOwner,
                    msg.getGroupName());
        }
        String content = success ? successStr : msg.getReason();
        ClientUtils.printSystemResponse(success, content);
    }
}
