package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/5/2 11:38
 **/
@Data
public class GroupMemberRequestMessage extends Message {
    private String username;
    private String groupName;

    public GroupMemberRequestMessage(String username, String groupName) {
        super(MessageType.GROUP_MEMBER_REQUEST);
        this.username = username;
        this.groupName = groupName;
    }
}
