package org.mytest.test.handler;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.ChatRoomServer;
import org.mytest.test.message.impl.GroupCreateRequestMessage;
import org.mytest.test.message.impl.GroupCreateResponseMessage;
import org.mytest.test.session.Session;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author gemo
 * @date 2022/5/1 18:03
 **/
@Slf4j
@ChannelHandler.Sharable
public class GroupCreateRequestHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupOwner = msg.getGroupOwner();
        String groupName = msg.getGroupName();
        Set<String> groupMembers = msg.getGroupMembers();
        if (StrUtil.isEmpty(groupOwner)
                || StrUtil.isEmpty(groupName)
                || groupMembers == null
                || groupMembers.size() < 3) {
            log.warn("发现不明消息：{}", msg);
            return;
        }
        Set<Session> members = groupMembers.stream()
                .map(ChatRoomServer.SERVER_MANAGER::getSession)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (members.size() < 3) {
            ctx.channel().writeAndFlush(new GroupCreateResponseMessage(false,
                    "在线成员数不得低于3人",
                    groupOwner,
                    groupName,
                    members.stream()
                            .map(Session::getUsername)
                            .collect(Collectors.toList())));
            return;
        }
        boolean result = ChatRoomServer.SERVER_MANAGER.groupCreate(groupOwner, groupName, members);
        GroupCreateResponseMessage response = new GroupCreateResponseMessage();
        response.setSuccess(result);
        response.setReason(result ? "" : "参数错误！");
        response.setGroupOwner(groupOwner);
        response.setGroupName(groupName);
        response.setMembers(members.stream()
                .map(Session::getUsername)
                .collect(Collectors.toList()));
        if (result) {
            members.stream()
                    .filter(item -> !item.getUsername().equals(groupOwner))
                    .forEach(item -> item.getChannel().writeAndFlush(response));
        }
        ctx.channel().writeAndFlush(response);
    }
}
