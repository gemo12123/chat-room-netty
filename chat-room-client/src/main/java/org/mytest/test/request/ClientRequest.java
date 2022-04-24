package org.mytest.test.request;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.ChatRoomClient;
import org.mytest.test.message.impl.ChatRequestMessage;

import java.util.Scanner;

/**
 * @author gemo
 * @date 2022/4/24 19:47
 **/
@Slf4j
public class ClientRequest extends Thread {

    private ChannelHandlerContext ctx;
    private Scanner scanner = new Scanner(System.in);

    public ClientRequest(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("=============" + Thread.currentThread().getName() + "=====================");
            System.out.println("send [username] [content]");
            System.out.println("gsend [group name] [content]");
            System.out.println("gcreate [group name] [m1,m2,m3...]");
            System.out.println("gmembers [group name]");
            System.out.println("gjoin [group name]");
            System.out.println("gquit [group name]");
            System.out.println("quit");
            System.out.println("==================================");
            String command = scanner.nextLine();
            String[] s = command.split(" ");
            int length = s.length;
            switch (s[0]) {
                case "send":
                    if (length < 3) {
                        System.err.println("非法参数！");
                        continue;
                    }
                    String username = ChatRoomClient.cm.getUsername();
                    String to = s[1];
                    String content = s[2];
                    ChatRequestMessage message = new ChatRequestMessage(username, to, content);
                    ctx.writeAndFlush(message);
                case "quit":
                    ctx.channel().close();
                    return;
                default:
                    System.err.println("非法参数！");
                    continue;
            }
        }
    }
}
