package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;

import java.util.List;

/**
 * @author gemo
 * @date 2022/5/1 20:47
 **/
@Data
public class GroupGetResponseMessage extends Message {
    private String username;
    private List<String> chatRoomCreate;
    private List<String> chatRoomJoin;
    private List<String> chatRoomAll;

    public GroupGetResponseMessage(String username, List<String> chatRoomCreate, List<String> chatRoomJoin, List<String> chatRoomAll) {
        super(MessageType.GROUP_GET_RESPONSE);
        this.username = username;
        this.chatRoomCreate = chatRoomCreate;
        this.chatRoomJoin = chatRoomJoin;
        this.chatRoomAll = chatRoomAll;
    }
}
