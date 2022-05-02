package org.mytest.test.manager;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.session.GroupSession;
import org.mytest.test.session.Session;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gemo
 * @date 2022/4/24 20:17
 **/
@Slf4j
public class ServerManagerImpl implements ServerManager {
    private Set<Session> clients = new HashSet<>();

    private List<GroupSession> groups = new ArrayList<>();

    @Override
    public boolean bind(String username, Channel channel) {
        if (username == null) {
            return false;
        }
        for (Session client : clients) {
            if (Objects.equals(client.getUsername(), username)) {
                return false;
            }
        }
        return clients.add(new Session(username, channel));
    }

    @Override
    public void unbind(String username) {
        if (StrUtil.isEmpty(username)) {
            return;
        }
        if (clients == null || clients.isEmpty()) {
            return;
        }
        Iterator<Session> iterator = clients.iterator();
        while (iterator.hasNext()) {
            Session session = iterator.next();
            String sessionUsername = session.getUsername();
            if (sessionUsername.equals(username)) {
                iterator.remove();
                return;
            }
        }
    }

    @Override
    public Session getSession(String username) {
        if (StrUtil.isEmpty(username)) {
            return null;
        }
        if (clients == null || clients.isEmpty()) {
            return null;
        }
        for (Session session : clients) {
            String sessionUsername = session.getUsername();
            if (sessionUsername.equals(username) && session.getChannel().isOpen()) {
                return session;
            }
        }
        return null;
    }

    @Override
    public Channel getChannel(String username) {
        Session session = getSession(username);
        if (session == null) {
            return null;
        }
        return session.getChannel();
    }

    @Override
    public boolean groupCreate(String groupOwner, String groupName, String... members) {
        if (StrUtil.isEmpty(groupOwner)
                || StrUtil.isEmpty(groupName)
                || members == null
                || members.length < 3) {
            log.warn("参数异常!groupOwner:{}, groupName:{}, members:{}!", groupOwner, groupName, members);
            return false;
        }
        GroupSession groupSession = new GroupSession();
        groupSession.setGroupName(groupName);
        groupSession.setGroupOwner(groupOwner);
        groupSession.setMembers(Arrays.stream(members)
                .map(this::getSession)
                .collect(Collectors.toSet()));
        return groups.add(groupSession);
    }

    @Override
    public boolean groupCreate(String groupOwner, String groupName, Set<Session> members) {
        if (StrUtil.isEmpty(groupOwner)
                || StrUtil.isEmpty(groupName)
                || members == null
                || members.size() < 3) {
            log.warn("参数异常!groupOwner:{}, groupName:{}, members:{}!", groupOwner, groupName, members);
            return false;
        }
        GroupSession groupSession = new GroupSession();
        groupSession.setGroupName(groupName);
        groupSession.setGroupOwner(groupOwner);
        groupSession.setMembers(members);
        return groups.add(groupSession);
    }

    @Override
    public boolean groupAdd(String groupName, Session session) {
        List<GroupSession> groups = Optional.ofNullable(this.groups)
                .orElseGet(ArrayList::new);
        this.groups = groups;
        for (GroupSession group : groups) {
            String groupNameTemp = group.getGroupName();
            if (!groupNameTemp.equals(groupName)) {
                continue;
            }
            return group.join(session);
        }
//        groupCreate(session.getUsername(), groupName);
        return false;
    }

    @Override
    public void groupAdd(String groupName, String username) {
        Session session = getSession(username);
        if (session == null) {
            return;
        }
        groupAdd(groupName, session);
    }

    @Override
    public List<GroupSession> getGroupList() {
        return groups;
    }

    @Override
    public GroupSession getGroup(String groupName) {
        GroupSession group = groups.stream()
                .filter(item -> item.getGroupName().equals(groupName))
                .findFirst()
                .get();
        return group;
    }


}
