package org.mytest.test.request;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.ChatRoomClient;
import org.mytest.test.message.impl.ChatRequestMessage;
import org.mytest.test.message.impl.GroupCreateRequestMessage;
import org.mytest.test.message.impl.GroupGetRequestMessage;

import java.util.Optional;
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
            printCommand();
            String command = scanner.nextLine();
            String[] content = command.split(" ", 2);
            switch (content[0]) {
                case "send":
                    Optional.ofNullable(ChatRequestMessage.of(content[1], ChatRoomClient.CLIENT_MANAGER.getCurrentUsername()))
                            .ifPresent(message -> ctx.writeAndFlush(message));
                    continue;
                case "gcreate":
                    Optional.ofNullable(GroupCreateRequestMessage.of(content[1],ChatRoomClient.CLIENT_MANAGER.getCurrentUsername()))
                            .ifPresent(message -> ctx.writeAndFlush(message));
                    continue;
                case "gget":
                    ctx.writeAndFlush(new GroupGetRequestMessage(ChatRoomClient.CLIENT_MANAGER.getCurrentUsername()));
                    continue;
                case "quit":
                    ctx.channel().close();
                    return;
                default:
                    System.err.println("非法参数！");
                    continue;
            }
        }
    }

    private void printCommand() {
        System.out.println("=============" + Thread.currentThread().getName() + "=====================");
        System.out.println("send [username] [content]");
        System.out.println("gsend [group name] [content]");
        System.out.println("gcreate [group name] [m1,m2,m3...]");
        System.out.println("gmembers [group name]");
        System.out.println("gjoin [group name]");
        System.out.println("gquit [group name]");
        System.out.println("gget");
        System.out.println("quit");
        System.out.println("==================================");
    }
}
