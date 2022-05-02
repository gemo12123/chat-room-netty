package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/5/2 10:41
 **/
@Data
public class GroupJoinResponseMessage extends Message {
    private boolean success;
    private String reason;
    private String joinUsername;
    private String joinGroupName;

    public GroupJoinResponseMessage(boolean success, String reason, String joinUsername, String joinGroupName) {
        super(MessageType.GROUP_JOIN_RESPONSE);
        this.success = success;
        this.reason = reason;
        this.joinUsername = joinUsername;
        this.joinGroupName = joinGroupName;
    }
}
