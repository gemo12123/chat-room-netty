package org.mytest.test.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mytest.test.ChatRoomClient;
import org.mytest.test.message.impl.GroupGetResponseMessage;

import java.util.List;
import java.util.Optional;

/**
 * @author gemo
 * @date 2022/5/1 21:03
 **/
public class GroupGetResponseHandler extends SimpleChannelInboundHandler<GroupGetResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupGetResponseMessage msg) throws Exception {
        String username = msg.getUsername();
        if (!ChatRoomClient.CLIENT_MANAGER.getCurrentUsername().equals(username)) {
            return;
        }
        List<String> chatRoomCreate = msg.getChatRoomCreate();
        List<String> chatRoomJoin = msg.getChatRoomJoin();
        List<String> chatRoomAll = msg.getChatRoomAll();
        printMessage(chatRoomCreate,chatRoomJoin,chatRoomAll);
    }

    private void printMessage(List<String> chatRoomCreate, List<String> chatRoomJoin, List<String> chatRoomAll) {
        System.out.println("========聊天室列表=========");
        System.out.println("您创建的聊天室");
        Optional.ofNullable(chatRoomCreate).ifPresent(System.out::println);
        System.out.println("您加入的聊天室");
        Optional.ofNullable(chatRoomJoin).ifPresent(System.out::println);
        System.out.println("其他聊天室");
        Optional.ofNullable(chatRoomAll).ifPresent(System.out::println);
        System.out.println("========聊天室列表=========");
    }
}
