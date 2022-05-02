package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/5/1 17:19
 **/
@Data
public class GroupQuitResponseMessage extends Message {
    private boolean success;
    private String reason;
    private String username;
    private String groupName;

    public GroupQuitResponseMessage(boolean success, String reason) {
        super(MessageType.GROUP_QUIT_RESPONSE);
        this.success = success;
        this.reason = reason;
    }
    public GroupQuitResponseMessage(boolean success, String reason, String username, String groupName) {
        super(MessageType.GROUP_QUIT_RESPONSE);
        this.success = success;
        this.reason = reason;
        this.username = username;
        this.groupName = groupName;
    }
}
