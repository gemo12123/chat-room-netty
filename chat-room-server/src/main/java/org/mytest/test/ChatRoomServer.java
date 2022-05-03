package org.mytest.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.codec.MessageCodec;
import org.mytest.test.codec.ProtocolFrameDecoder;
import org.mytest.test.handler.*;
import org.mytest.test.manager.ServerManager;
import org.mytest.test.manager.ServerManagerImpl;

/**
 * @author gemo
 * @date 2022/4/17 16:28
 **/
@Slf4j
public class ChatRoomServer {
    public static final ServerManager SERVER_MANAGER = new ServerManagerImpl();

    public static final ChannelHandler LOGGING_HANDLER = new LoggingHandler();
    public static final ChannelHandler MESSAGE_CODEC = new MessageCodec();
    // 心跳handler
    public static final ChannelHandler HEAT_BEAT_HADNLER = new HeartBeatHandler();
    // 各种handler
    public static final ChannelHandler LOGIN_HANDLER = new LoginHandler();
    public static final ChannelHandler CHAT_REQUEST_HANDLER = new ChatRequestHandler();
    public static final ChannelHandler GROUP_CREATE_HANDLER = new GroupCreateRequestHandler();
    public static final ChannelHandler GROUP_GET_HANDLER = new GroupGetRequestHandler();
    public static final ChannelHandler GROUP_JOIN_HANDLER = new GroupJoinRequestHandler();
    public static final ChannelHandler GROUP_MEMBER_HANDLER = new GroupMemberRequestHandler();
    public static final ChannelHandler GROUP_QUIT_HANDLER = new GroupQuitRequestHandler();
    public static final ChannelHandler GROUP_CHAT_REQUEST_HANDLER = new GroupChatRequestHandler();

    public static void main(String[] args) {
        NioEventLoopGroup acceptorExecutor = new NioEventLoopGroup();
        NioEventLoopGroup workerExecutor = new NioEventLoopGroup();
        try {
            new ServerBootstrap()
                    .group(acceptorExecutor)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtocolFrameDecoder());
//                            ch.pipeline().addLast(LOGGING_HANDLER);
                            ch.pipeline().addLast(MESSAGE_CODEC);
                            // 5s没有读取消息，则触发IdleStateEvent#READER_IDLE事件
                            ch.pipeline().addLast(workerExecutor, new IdleStateHandler(5,0,0));
                            ch.pipeline().addLast(workerExecutor, HEAT_BEAT_HADNLER);
                            // 业务handler
                            ch.pipeline().addLast(workerExecutor, LOGIN_HANDLER);
                            ch.pipeline().addLast(workerExecutor, CHAT_REQUEST_HANDLER);
                            ch.pipeline().addLast(workerExecutor, GROUP_CREATE_HANDLER);
                            ch.pipeline().addLast(workerExecutor, GROUP_GET_HANDLER);
                            ch.pipeline().addLast(workerExecutor, GROUP_JOIN_HANDLER);
                            ch.pipeline().addLast(workerExecutor, GROUP_MEMBER_HANDLER);
                            ch.pipeline().addLast(workerExecutor, GROUP_QUIT_HANDLER);
                            ch.pipeline().addLast(workerExecutor, GROUP_CHAT_REQUEST_HANDLER);
                        }
                    })
                    .bind(8080)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
        } catch (Exception e) {
            log.error("出现异常！！！", e);
        } finally {
            acceptorExecutor.shutdownGracefully();
            workerExecutor.shutdownGracefully();
        }
    }
}
