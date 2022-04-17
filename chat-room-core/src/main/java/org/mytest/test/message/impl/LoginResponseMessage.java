package org.mytest.test.message.impl;

import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/4/17 17:26
 **/
public class LoginResponseMessage extends Message {

    private boolean success;

    private String reason;

    public LoginResponseMessage(boolean success, String reason) {
        super(MessageType.LOGIN_RESPONSE);
        this.success = success;
        this.reason = reason;
    }

    public static LoginResponseMessage loginSuccess(String username) {
        return new LoginResponseMessage(true, username + "登陆成功！");
    }

    public static LoginResponseMessage loginFail(String reason) {
        return new LoginResponseMessage(true, reason);
    }
}
