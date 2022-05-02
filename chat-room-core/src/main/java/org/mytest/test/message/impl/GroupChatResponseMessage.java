package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/5/1 17:18
 **/
@Data
public class GroupChatResponseMessage extends Message {
    private boolean success;
    private String reason;

    public GroupChatResponseMessage(boolean success, String reason) {
        super(MessageType.GROUP_CHAT_RESPONSE);
        this.success = success;
        this.reason = reason;
    }
}
