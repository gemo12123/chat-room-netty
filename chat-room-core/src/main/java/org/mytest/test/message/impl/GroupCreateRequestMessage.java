package org.mytest.test.message.impl;

import lombok.Data;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageType;
import org.mytest.test.session.Session;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author gemo
 * @date 2022/5/1 17:17
 **/
@Data
public class GroupCreateRequestMessage extends Message {
    private String groupOwner;
    private String groupName;
    private Set<String> groupMembers;

    public GroupCreateRequestMessage(String groupOwner, String groupName, Set<String> groupMembers) {
        super(MessageType.GROUP_CREATE_REQUEST);
        this.groupOwner = groupOwner;
        this.groupName = groupName;
        this.groupMembers = groupMembers;
    }

    public static GroupCreateRequestMessage of(String command, String groupOwner) {
        String[] groupSplit = command.split(" ", 2);
        if (groupSplit.length < 2) {
            System.err.println("非法参数！");
            return null;
        }
        String[] memberSplit = groupSplit[1].split(",");
        if (memberSplit.length < 2) {
            System.err.println("群组成员数量必须大于2！");
            return null;
        }
        Set<String> members = Arrays.stream(memberSplit)
                .collect(Collectors.toSet());
        members.add(groupOwner);
        if (members.size() < 2) {
            System.err.println("群组成员数量必须大于2！");
            return null;
        }
        GroupCreateRequestMessage message = new GroupCreateRequestMessage(groupOwner, groupSplit[0], members);
        return message;
    }
}
