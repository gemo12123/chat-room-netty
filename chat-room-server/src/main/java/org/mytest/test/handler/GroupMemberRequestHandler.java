package org.mytest.test.handler;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.ChatRoomServer;
import org.mytest.test.message.impl.GroupMemberRequestMessage;
import org.mytest.test.message.impl.GroupMemberResponseMessage;
import org.mytest.test.session.GroupSession;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gemo
 * @date 2022/5/2 11:42
 **/
@Slf4j
@ChannelHandler.Sharable
public class GroupMemberRequestHandler extends SimpleChannelInboundHandler<GroupMemberRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMemberRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String groupName = msg.getGroupName();
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(groupName)) {
            log.info("发现错误消息！错误消息：{}", msg);
            return;
        }
        GroupSession group = ChatRoomServer.SERVER_MANAGER.getGroup(groupName);
        if (group == null) {
            ctx.channel().writeAndFlush(new GroupMemberResponseMessage(false, "未找到名为 \"" + groupName + "\" 聊天室！"));
            return;
        }
        List<String> members = group.getMembers()
                .stream()
                .map(item -> item.getUsername())
                .collect(Collectors.toList());
        if (!members.contains(username)) {
            ctx.channel().writeAndFlush(new GroupMemberResponseMessage(false, "您不在 \"" + groupName + "\" 聊天室内！"));
            return;
        }
        ctx.channel().writeAndFlush(new GroupMemberResponseMessage(true, "", groupName, group.getGroupOwner(), members));
    }
}
