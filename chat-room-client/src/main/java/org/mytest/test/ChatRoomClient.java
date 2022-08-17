package org.mytest.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.codec.MessageCodec;
import org.mytest.test.codec.ProtocolFrameDecoder;
import org.mytest.test.handler.*;
import org.mytest.test.manager.ClientManager;
import org.mytest.test.manager.ClientManagerImpl;
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
    public static final ClientManager CLIENT_MANAGER=new ClientManagerImpl();

    public static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(5, 20, 5, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(200),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static final ProtocolFrameDecoder PROTOCOL_FRAME_DECODER = new ProtocolFrameDecoder();
    public static final ChannelHandler LOGGING_HANDLER = new LoggingHandler();
    public static final ChannelHandler MESSAGE_CODEC = new MessageCodec();
    // 心跳handler
    public static final ChannelHandler HEAT_BEAT_HANDLER = new HeartBeatHandler();
    // 各种Handler
    public static final ChannelHandler LOGIN_HANDLER = new LoginHandler();
    public static final ChannelHandler CHAT_REQUEST_HANDLER = new ChatRequestHandler();
    public static final ChannelHandler CHAT_RESPONSE_HANDLER = new ChatResponseHandler();
    public static final ChannelHandler GROUP_RESPONSE_HANDLER = new GroupCreateResponseHandler();
    public static final ChannelHandler GROUP_GET_HANDLER = new GroupGetResponseHandler();
    public static final ChannelHandler GROUP_JOIN_HANDLER = new GroupJoinResponseHandler();
    public static final ChannelHandler GROUP_MEMBER_HANDLER = new GroupMemberResponseHandler();
    public static final ChannelHandler GROUP_QUIT_HANDLER = new GroupQuitResponseHandler();
    public static final ChannelHandler GROUP_CHAT_REQUEST_HANDLER = new GroupChatRequestHandler();
    public static final ChannelHandler GROUP_CHAT_RESPONSE_HANDLER = new GroupChatResponseHandler();

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            new Bootstrap()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                    .group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(PROTOCOL_FRAME_DECODER);
                            ch.pipeline().addLast(MESSAGE_CODEC);
//                            ch.pipeline().addLast(LOGGING_HANDLER);
                            // 心跳handler
                            ch.pipeline().addLast(new IdleStateHandler(0,2,0));
                            ch.pipeline().addLast(HEAT_BEAT_HANDLER);
                            // 建立连接时的处理器
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                /**
                                 * 连接建立时
                                 * @param ctx
                                 * @throws Exception
                                 */
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

                                /**
                                 * 连接断开时
                                 * @param ctx
                                 * @throws Exception
                                 */
                                @Override
                                public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                    log.info("连接已经断开!");
                                    if (!eventExecutors.isShutdown()) {
                                        eventExecutors.shutdownGracefully();
                                    }
                                    if (!THREAD_POOL.isShutdown()) {
                                        THREAD_POOL.shutdownNow();
                                    }
                                }

                                /**
                                 * 连接异常时
                                 * @param ctx
                                 * @param cause
                                 * @throws Exception
                                 */
                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    log.error("Client link is error!", cause);
                                }
                            });
                            ch.pipeline().addLast(LOGIN_HANDLER);
                            ch.pipeline().addLast(CHAT_REQUEST_HANDLER);
                            ch.pipeline().addLast(CHAT_RESPONSE_HANDLER);
                            ch.pipeline().addLast(GROUP_RESPONSE_HANDLER);
                            ch.pipeline().addLast(GROUP_GET_HANDLER);
                            ch.pipeline().addLast(GROUP_JOIN_HANDLER);
                            ch.pipeline().addLast(GROUP_MEMBER_HANDLER);
                            ch.pipeline().addLast(GROUP_QUIT_HANDLER);
                            ch.pipeline().addLast(GROUP_CHAT_REQUEST_HANDLER);
                            ch.pipeline().addLast(GROUP_CHAT_RESPONSE_HANDLER);
                        }
                    })
                    .connect("127.0.0.1", 8080)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
        } catch (Exception e) {
            log.error("出现异常！！！", e);
        } finally {
            log.info("{}线程执行，关闭了资源！", Thread.currentThread().getName());
            if (!eventExecutors.isShutdown()) {
                eventExecutors.shutdownGracefully();
            }
            // TODO: 2022/5/2 线程池无法关闭,clientRequest线程未关闭！
            if (!THREAD_POOL.isShutdown()) {
                THREAD_POOL.shutdownNow();
            }
        }
    }
}
