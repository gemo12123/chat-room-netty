package org.mytest.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.codec.MessageCodec;
import org.mytest.test.codec.ProtocolFrameDecoder;
import org.mytest.test.handler.LoginHandler;
import org.mytest.test.message.impl.LoginRequestMessage;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author gemo
 * @date 2022/4/19 18:59
 **/
@Slf4j
public class ChatRoomClient {
    public static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(5, 20, 5, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(200),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());
    public static final ChannelHandler LOGGING_HANDLER = new LoggingHandler();
    public static final ChannelHandler MESSAGE_CODEC = new MessageCodec();

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        NioEventLoopGroup workerExecutor = new NioEventLoopGroup();
        try {
            new Bootstrap()
                    .group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtocolFrameDecoder());
                            ch.pipeline().addLast(LOGGING_HANDLER);
                            ch.pipeline().addLast(MESSAGE_CODEC);
                            // 建立连接时的处理器
                            ch.pipeline().addLast(workerExecutor, new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    THREAD_POOL.execute(() -> {
                                        System.out.println("请输入账号：");
                                        String username = scanner.nextLine();
                                        System.out.println("请输入密码：");
                                        String password = scanner.nextLine();
                                        LoginRequestMessage loginRequestMessage = new LoginRequestMessage(username, password);
                                        ctx.writeAndFlush(loginRequestMessage);
                                    });
                                }
                            });
                            ch.pipeline().addLast(workerExecutor, new LoginHandler());
                        }
                    })
                    .connect("127.0.0.1", 8080)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
            System.out.println("结束====================");
        } catch (Exception e) {
            log.error("出现异常！！！", e);
        } finally {
            log.info("{}线程执行，关闭了资源！", Thread.currentThread().getName());
            eventExecutors.shutdownGracefully();
            workerExecutor.shutdownGracefully();
            THREAD_POOL.shutdown();
        }
    }
}
