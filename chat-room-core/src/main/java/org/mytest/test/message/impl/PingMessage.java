package org.mytest.test.message.impl;

import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/4/17 11:30
 **/
public class PingMessage extends Message {
    public PingMessage() {
        super(MessageType.PING);
    }
}
