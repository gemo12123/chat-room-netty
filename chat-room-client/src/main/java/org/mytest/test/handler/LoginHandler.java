package org.mytest.test.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.ChatRoomClient;
import org.mytest.test.message.impl.LoginResponseMessage;
import org.mytest.test.request.ClientRequest;
import org.mytest.test.util.ClientUtils;

/**
 * @author gemo
 * @date 2022/4/19 19:07
 **/
@Slf4j
@ChannelHandler.Sharable
public class LoginHandler extends SimpleChannelInboundHandler<LoginResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponseMessage msg) throws Exception {
        if (!msg.isSuccess()) {
            ClientUtils.printSystemResponse(false,msg.getReason());
            ctx.channel().close();
            return;
        }
        ChatRoomClient.CLIENT_MANAGER.register(msg.getUsername());
        ChatRoomClient.THREAD_POOL.execute(new ClientRequest(ctx));
    }
}
