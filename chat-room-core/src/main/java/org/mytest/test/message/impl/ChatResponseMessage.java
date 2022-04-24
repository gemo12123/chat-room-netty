package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/4/24 19:58
 **/
@Data
public class ChatResponseMessage extends Message {
    private String from;
    private String content;

    public ChatResponseMessage(String from, String content) {
        super(MessageType.CHAT_REQUEST);
        this.from = from;
        this.content = content;
    }
}
