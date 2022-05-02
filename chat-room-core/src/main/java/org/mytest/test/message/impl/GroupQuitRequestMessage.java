package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/5/1 17:18
 **/
@Data
public class GroupQuitRequestMessage extends Message {
    private String username;
    private String groupName;

    public GroupQuitRequestMessage(String username, String groupName) {
        super(MessageType.GROUP_QUIT_REQUEST);
        this.username = username;
        this.groupName = groupName;
    }
}
