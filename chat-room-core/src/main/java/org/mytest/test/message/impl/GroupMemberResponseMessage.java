package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

import java.util.List;

/**
 * @author gemo
 * @date 2022/5/2 11:39
 **/
@Data
public class GroupMemberResponseMessage extends Message {
    private boolean success;
    private String reason;
    private String groupName;
    private String groupOwner;
    private List<String> members;

    public GroupMemberResponseMessage(boolean success, String reason) {
        super(MessageType.GROUP_MEMBER_RESPONSE);
        this.success = success;
        this.reason = reason;
    }

    public GroupMemberResponseMessage(boolean success, String reason, String groupName, String groupOwner, List<String> members) {
        super(MessageType.GROUP_MEMBER_RESPONSE);
        this.success = success;
        this.reason = reason;
        this.groupName = groupName;
        this.groupOwner = groupOwner;
        this.members = members;
    }
}
