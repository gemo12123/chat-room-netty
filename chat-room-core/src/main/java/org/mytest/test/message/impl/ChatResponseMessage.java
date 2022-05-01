package org.mytest.test.message.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/4/24 19:58
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatResponseMessage extends Message {
    private String from;
    private boolean success;
    private String content;

    public ChatResponseMessage(String from, boolean success, String content) {
        super(MessageType.CHAT_REQUEST);
        this.from = from;
        this.success = success;
        this.content = content;
    }
}
