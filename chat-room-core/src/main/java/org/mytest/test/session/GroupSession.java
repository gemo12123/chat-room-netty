package org.mytest.test.session;

import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author gemo
 * @date 2022/4/24 20:27
 **/
@Data
public class GroupSession {
    private String groupName;

    private String groupOwner;

    private List<Session> members;

    public void join(Session session) {
        List<Session> group = Optional.ofNullable(this.members)
                .orElseGet(ArrayList::new);
        this.members = group;
        group.add(session);
    }

    public void exist(String username) {
        if (members == null || members.isEmpty()) {
            return;
        }
        Iterator<Session> iterator = members.iterator();
        while (iterator.hasNext()) {
            Session session = iterator.next();
            if (session.getUsername().equals(username)) {
                iterator.remove();
                return;
            }
        }
    }
}
