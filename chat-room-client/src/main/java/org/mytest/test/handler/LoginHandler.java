package org.mytest.test.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.ChatRoomClient;
import org.mytest.test.message.impl.LoginResponseMessage;

import java.util.Scanner;

/**
 * @author gemo
 * @date 2022/4/19 19:07
 **/
@Slf4j
public class LoginHandler extends SimpleChannelInboundHandler<LoginResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponseMessage msg) throws Exception {
        log.info("得到结果：{}", msg);
        if (!msg.isSuccess()) {
            log.info("账号输入错误！");
            ctx.channel().close();
            return;
        }
        ChatRoomClient.THREAD_POOL.execute(() -> {
            Scanner scanner = new Scanner(System.in);
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
                switch (s[0]) {
                    case "quit":
                        ctx.channel().close();
                        return;
                }
            }
        });
    }
}
