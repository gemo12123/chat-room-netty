package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/4/17 17:24
 **/
@Data
public class LoginRequestMessage extends Message {

    private String username;

    private String password;

    public LoginRequestMessage(String username,String password) {
        super(MessageType.LOGIN_REQUEST);
        this.username=username;
        this.password=password;
    }
}
