package org.mytest.test.session;

import lombok.Data;

import java.util.*;

/**
 * @author gemo
 * @date 2022/4/24 20:27
 **/
@Data
public class GroupSession {
    private String groupName;

    private String groupOwner;

    private Set<Session> members;

    public boolean join(Session session) {
        Set<Session> group = Optional.ofNullable(this.members)
                .orElseGet(HashSet::new);
        this.members = group;
        return group.add(session);
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
