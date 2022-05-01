package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/5/1 20:47
 **/
@Data
public class GroupGetRequestMessage extends Message {
    private String username;

    public GroupGetRequestMessage(String username) {
        super(MessageType.GROUP_GET_REQUEST);
        this.username = username;
    }
}
