package org.mytest.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.codec.MessageCodec;
import org.mytest.test.codec.ProtocolFrameDecoder;
import org.mytest.test.handler.LoginHandler;

/**
 * @author gemo
 * @date 2022/4/17 16:28
 **/
@Slf4j
public class ChatRoomServer {
    public static final ChannelHandler LOGGING_HANDLER = new LoggingHandler();
    public static final ChannelHandler MESSAGE_CODEC = new MessageCodec();
    public static final ChannelHandler LOGIN_HANDLER = new LoginHandler();

    public static void main(String[] args) {
        NioEventLoopGroup acceptorExecute = new NioEventLoopGroup();
        NioEventLoopGroup workerExecutor = new NioEventLoopGroup();
        new ServerBootstrap()
                .group(acceptorExecute)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ProtocolFrameDecoder());
                        ch.pipeline().addLast(LOGGING_HANDLER);
                        ch.pipeline().addLast(MESSAGE_CODEC);
                        ch.pipeline().addLast(workerExecutor, LOGIN_HANDLER);

                    }
                })
                .bind(8080);

        // 注册进程钩子，在JVM进程关闭前释放资源
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("{}现程执行，关闭了资源！", Thread.currentThread().getName());
            acceptorExecute.shutdownGracefully();
            workerExecutor.shutdownGracefully();
        }));
    }
}
