package org.mytest.test.handler;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.ChatRoomServer;
import org.mytest.test.manager.ServerManager;
import org.mytest.test.message.impl.ChatRequestMessage;
import org.mytest.test.message.impl.ChatResponseMessage;

import java.util.Optional;

/**
 * @author gemo
 * @date 2022/5/1 10:31
 **/
@Slf4j
@ChannelHandler.Sharable
public class ChatRequestHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        String from = msg.getFrom();
        String to = msg.getTo();
        String content = msg.getContent();
        if (StrUtil.isEmpty(from) || StrUtil.isEmpty(to) || StrUtil.isEmpty(content)) {
            log.warn("发现不明消息：{}", msg);
            return;
        }
        ServerManager serverManager = ChatRoomServer.SERVER_MANAGER;
        ChatResponseMessage responseMessage = null;
        Channel channel = serverManager.getChannel(to);
        if (channel != null && channel.isOpen()) {
            channel.writeAndFlush(msg);
            responseMessage = new ChatResponseMessage(from, true, "消息发送成功！");
        } else {
            responseMessage = new ChatResponseMessage(from, false, to + "不在线！");
        }
        ctx.channel().writeAndFlush(responseMessage);
    }
}
