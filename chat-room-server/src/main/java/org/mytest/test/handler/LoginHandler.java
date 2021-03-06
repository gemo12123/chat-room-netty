package org.mytest.test.handler;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.ChatRoomServer;
import org.mytest.test.message.impl.LoginRequestMessage;
import org.mytest.test.message.impl.LoginResponseMessage;
import org.mytest.test.session.service.UserServiceFactory;

/**
 * @author gemo
 * @date 2022/4/17 17:54
 **/
@Slf4j
@ChannelHandler.Sharable
public class LoginHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
            log.info("用户名或密码为空！");
            ctx.writeAndFlush(LoginResponseMessage.loginFail("用户名或密码为空！"));
            return;
        }
        boolean loginResult = UserServiceFactory.getUserService()
                .login(username, password);
        log.info("用户{}的登陆结果{}！", username, loginResult ? "成功" : "失败");
        if (!loginResult) {
            ctx.writeAndFlush(LoginResponseMessage.loginFail("密码错误！"));
        }
        LoginResponseMessage message = null;
        boolean result = ChatRoomServer.SERVER_MANAGER.bind(username, ctx.channel());
        if (result) {
            message = LoginResponseMessage.loginSuccess(username);
        } else {
            message = LoginResponseMessage.loginFail("用户已登陆！");
        }
        ctx.writeAndFlush(message);
    }
}
