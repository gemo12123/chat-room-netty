package org.mytest.test.message.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/4/17 17:26
 **/
@Data
public class LoginResponseMessage extends Message {

    private boolean success;

    private String reason;

    private String username;

    public LoginResponseMessage() {
        super(MessageType.LOGIN_RESPONSE);
    }

    public LoginResponseMessage(boolean success, String reason) {
        super(MessageType.LOGIN_RESPONSE);
        this.success = success;
        this.reason = reason;
    }

    public static LoginResponseMessage loginSuccess(String username) {
        LoginResponseMessage loginResponseMessage = new LoginResponseMessage(true, username + "登陆成功！");
        loginResponseMessage.setUsername(username);
        return loginResponseMessage;
    }

    public static LoginResponseMessage loginFail(String reason) {
        return new LoginResponseMessage(false, reason);
    }
}
