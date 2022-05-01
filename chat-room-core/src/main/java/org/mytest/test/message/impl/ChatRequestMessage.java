package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.manager.ClientManager;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

/**
 * @author gemo
 * @date 2022/4/24 19:56
 **/
@Data
public class ChatRequestMessage extends Message {
    private String from;
    private String to;
    private String content;

    public ChatRequestMessage(String from, String to, String content) {
        super(MessageType.CHAT_REQUEST);
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public static ChatRequestMessage of(String message,String username) {
        String[] command = message.split(" ", 2);
        if (command.length < 2) {
            System.err.println("非法参数！");
            return null;
        }
        String to = command[0];
        String content = command[1];
        return new ChatRequestMessage(username, to, content);

    }
}
