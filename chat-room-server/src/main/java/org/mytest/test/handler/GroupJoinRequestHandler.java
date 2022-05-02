package org.mytest.test.handler;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.ChatRoomServer;
import org.mytest.test.message.impl.GroupJoinRequestMessage;
import org.mytest.test.message.impl.GroupJoinResponseMessage;
import org.mytest.test.session.GroupSession;
import org.mytest.test.session.Session;

import java.util.Objects;

/**
 * @author gemo
 * @date 2022/5/2 10:50
 **/
@Slf4j
@ChannelHandler.Sharable
public class GroupJoinRequestHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String groupName = msg.getGroupName();
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(groupName)) {
            log.info("发现错误消息！消息内容：{}", msg);
            return;
        }
        GroupSession group = ChatRoomServer.SERVER_MANAGER.getGroup(groupName);
        if (group == null) {
            GroupJoinResponseMessage messsge = new GroupJoinResponseMessage(false, "无此聊天室！", username, groupName);
            ctx.channel().writeAndFlush(messsge);
            return;
        }
        Session session = ChatRoomServer.SERVER_MANAGER.getSession(username);
        if (session == null) {
            GroupJoinResponseMessage messsge = new GroupJoinResponseMessage(false, "未登陆的用户！", username, groupName);
            ctx.channel().writeAndFlush(messsge);
            return;
        }
        boolean result = ChatRoomServer.SERVER_MANAGER.groupAdd(groupName, session);
        if (!result) {
            GroupJoinResponseMessage messsge = new GroupJoinResponseMessage(false, "您已在该聊天室中！", username, groupName);
            ctx.channel().writeAndFlush(messsge);
            return;
        }
        GroupJoinResponseMessage messsge = new GroupJoinResponseMessage(true, "", username, groupName);
        group.getMembers()
                .stream()
                .filter(item -> !Objects.equals(item.getUsername(), username))
                .map(Session::getChannel)
                .filter(Objects::nonNull)
                .filter(Channel::isOpen)
                .forEach(item -> item.writeAndFlush(messsge));
        ctx.writeAndFlush(messsge);
    }
}
