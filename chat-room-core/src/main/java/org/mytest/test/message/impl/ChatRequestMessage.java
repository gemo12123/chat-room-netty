package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/4/24 19:56
 **/
@Data
public class ChatRequestMessage extends Message {
    private String from;
    private String to;
    private String content;

    public ChatRequestMessage(String from, String to, String content) {
        super(MessageType.CHAT_REQUEST);
        this.from = from;
        this.to = to;
        this.content = content;
    }
}
