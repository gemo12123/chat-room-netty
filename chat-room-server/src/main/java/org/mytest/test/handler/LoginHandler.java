package org.mytest.test.handler;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.mytest.test.message.impl.LoginRequestMessage;
import org.mytest.test.message.impl.LoginResponseMessage;
import org.mytest.test.session.service.UserServiceFactory;

/**
 * @author gemo
 * @date 2022/4/17 17:54
 **/
@ChannelHandler.Sharable
public class LoginHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
            ctx.writeAndFlush(LoginResponseMessage.loginFail("用户名或密码为空！"));
            return;
        }
        boolean loginResult = UserServiceFactory.getUserService()
                .login(username, password);

        ctx.writeAndFlush(loginResult ? LoginResponseMessage.loginSuccess(username)
                : LoginResponseMessage.loginFail("密码错误！"));
    }
}
