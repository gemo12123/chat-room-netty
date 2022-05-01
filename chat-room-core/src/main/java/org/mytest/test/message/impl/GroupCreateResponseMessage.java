package org.mytest.test.message.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

import java.util.List;

/**
 * @author gemo
 * @date 2022/5/1 17:17
 **/
@Data
public class GroupCreateResponseMessage extends Message {
    private boolean success;
    private String reason;
    private String groupOwner;
    private String groupName;
    private List<String> members;

    public GroupCreateResponseMessage() {
        super(MessageType.GROUP_CREATE_RESPONSE);
    }

    public GroupCreateResponseMessage(boolean success, String reason, String groupOwner, String groupName, List<String> members) {
        super(MessageType.GROUP_CREATE_RESPONSE);
        this.success = success;
        this.reason = reason;
        this.groupOwner = groupOwner;
        this.groupName = groupName;
        this.members = members;
    }
}
