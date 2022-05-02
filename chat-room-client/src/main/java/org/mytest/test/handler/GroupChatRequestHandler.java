package org.mytest.test.handler;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.message.impl.GroupChatRequestMessage;

/**
 * @author gemo
 * @date 2022/5/2 17:27
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
        printMessage(username,groupName,content);
    }

    private void printMessage(String username, String groupName, String content) {
        System.out.println("-----------" + groupName + "-------------");
        System.out.println(username + ": " + content);
        System.out.println("------------------------");
    }
}
