package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/5/2 10:40
 **/
@Data
public class GroupJoinRequestMessage extends Message {
    private String username;
    private String groupName;

    public GroupJoinRequestMessage(String username, String groupName) {
        super(MessageType.GROUP_JOIN_REQUEST);
        this.username = username;
        this.groupName = groupName;
    }

    public static GroupJoinRequestMessage of(String groupName, String username) {
        GroupJoinRequestMessage message = new GroupJoinRequestMessage(username, groupName);
        return message;
    }
}
