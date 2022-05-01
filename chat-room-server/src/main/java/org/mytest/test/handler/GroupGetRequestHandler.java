package org.mytest.test.handler;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.ChatRoomServer;
import org.mytest.test.message.impl.GroupGetRequestMessage;
import org.mytest.test.message.impl.GroupGetResponseMessage;
import org.mytest.test.session.GroupSession;
import org.mytest.test.session.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gemo
 * @date 2022/5/1 20:54
 **/
@Slf4j
@ChannelHandler.Sharable
public class GroupGetRequestHandler extends SimpleChannelInboundHandler<GroupGetRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupGetRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        if (StrUtil.isEmpty(username)) {
            log.info("未读取到有效信息！");
            return;
        }
        List<GroupSession> groupList = ChatRoomServer.SERVER_MANAGER.getGroupList();
        List<String> groupCreate = new ArrayList<>();
        List<String> groupJoin = new ArrayList<>();
        List<String> groupAll = new ArrayList<>();
        for (GroupSession groupSession : groupList) {
            String groupOwner = groupSession.getGroupOwner();
            String groupName = groupSession.getGroupName();
            if (groupOwner.equals(username)) {
                groupCreate.add(groupName);
                continue;
            }
            List<String> members = groupSession.getMembers()
                    .stream()
                    .map(Session::getUsername)
                    .collect(Collectors.toList());
            if (members.contains(username)) {
                groupJoin.add(groupName);
                continue;
            }
            groupAll.add(groupName);
        }
        GroupGetResponseMessage message = new GroupGetResponseMessage(username, groupCreate, groupJoin, groupAll);
        ctx.channel().writeAndFlush(message);
    }
}
