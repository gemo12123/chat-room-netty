package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/5/1 17:18
 **/
@Data
public class GroupChatRequestMessage extends Message {
    private String username;
    private String groupName;
    private String content;

    public GroupChatRequestMessage(String username, String groupName, String content) {
        super(MessageType.GROUP_CHAT_REQUEST);
        this.username = username;
        this.groupName = groupName;
        this.content = content;
    }

    public static GroupChatRequestMessage of(String command,String username){
        String[] commandSplit = command.split(" ", 2);
        if (commandSplit.length < 2) {
            System.err.println("非法参数！");
            return null;
        }
        return new GroupChatRequestMessage(username, commandSplit[0], commandSplit[1]);
    }
}
