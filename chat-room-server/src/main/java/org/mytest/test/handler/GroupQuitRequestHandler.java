package org.mytest.test.handler;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.ChatRoomServer;
import org.mytest.test.message.impl.GroupQuitRequestMessage;
import org.mytest.test.message.impl.GroupQuitResponseMessage;
import org.mytest.test.session.GroupSession;
import org.mytest.test.session.Session;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * @author gemo
 * @date 2022/5/2 16:32
 **/
@Slf4j
@ChannelHandler.Sharable
public class GroupQuitRequestHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String groupName = msg.getGroupName();
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(groupName)) {
            log.info("发现错误消息！错误消息：{}", msg);
            return;
        }
        GroupSession group = ChatRoomServer.SERVER_MANAGER.getGroup(groupName);
        if (group == null) {
            GroupQuitResponseMessage message = new GroupQuitResponseMessage(false, "\"" + groupName + "\" 聊天室不存在！");
            ctx.writeAndFlush(message);
            return;
        }
        // TODO: 2022/5/2 需特殊处理群主退出的场景，可能导致群主退出时gmembers获取的信息不准确
        Set<Session> memberSessions = group.getMembers();
//        Iterator<Session> iterator = memberSessions.iterator();
//        while (iterator.hasNext()) {
//            Session next = iterator.next();
//            if (Objects.equals(next.getUsername(), username)) {
//                iterator.remove();
//                GroupQuitResponseMessage message = new GroupQuitResponseMessage(true, "", username, groupName);
//                ctx.writeAndFlush(message);
//                return;
//            }
//        }
        boolean result = memberSessions.removeIf(item -> item.getUsername().equals(username));
        if (result) {
            GroupQuitResponseMessage message = new GroupQuitResponseMessage(true, "", username, groupName);
            ctx.writeAndFlush(message);
            memberSessions.forEach(item->item.getChannel().writeAndFlush(message));
        }else{
            GroupQuitResponseMessage message = new GroupQuitResponseMessage(false, "您不在 \"" + groupName + "\" 聊天室中！");
            ctx.writeAndFlush(message);
        }
    }
}
