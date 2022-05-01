package org.mytest.test.handler;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.message.impl.ChatRequestMessage;

/**
 * @author gemo
 * @date 2022/5/1 10:39
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
        printMessage(msg);
    }

    private void printMessage(ChatRequestMessage msg) {
        String from = msg.getFrom();
        String content = msg.getContent();
        System.out.println("-----------" + from + "-------------");
        System.out.println(from + ": " + content);
        System.out.println("------------------------");
    }
}
