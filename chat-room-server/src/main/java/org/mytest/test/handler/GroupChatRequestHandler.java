package org.mytest.test.handler;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.ChatRoomServer;
import org.mytest.test.message.impl.GroupChatRequestMessage;
import org.mytest.test.message.impl.GroupChatResponseMessage;
import org.mytest.test.session.GroupSession;
import org.mytest.test.session.Session;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gemo
 * @date 2022/5/2 17:14
 **/
@Slf4j
@ChannelHandler.Sharable
public class GroupChatRequestHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String groupName = msg.getGroupName();
        String content = msg.getContent();
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(groupName) || StrUtil.isEmpty(content)) {
            log.info("发现无用消息：{}！", msg);
            return;
        }
        GroupSession group = ChatRoomServer.SERVER_MANAGER.getGroup(groupName);
        if (group == null) {
            ctx.channel().writeAndFlush(new GroupChatResponseMessage(false, "\"" + groupName + "\" 聊天室不存在！"));
            return;
        }
        Set<Session> members = group.getMembers();
        if (!members.stream()
                .map(Session::getUsername)
                .collect(Collectors.toList())
                .contains(username)) {
            ctx.channel().writeAndFlush(new GroupChatResponseMessage(false, "您尚未加入 \"" + groupName + "\" 聊天室！"));
            return;
        }
        members.stream()
                .map(Session::getChannel)
                .filter(Objects::nonNull)
                .filter(Channel::isOpen)
                .forEach(item -> item.writeAndFlush(msg));
        ctx.channel().writeAndFlush(new GroupChatResponseMessage(true, "消息成功发送至 \"" + groupName + "\" 聊天室！"));
    }
}
