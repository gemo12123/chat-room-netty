package org.mytest.test.manager;

import cn.hutool.core.util.StrUtil;
import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;
import org.mytest.test.session.GroupSession;
import org.mytest.test.session.Session;

import io.netty.channel.Channel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gemo
 * @date 2022/4/24 20:17
 **/
public class ServerManagerImpl implements ServerManager {
    private List<Session> clients;

    private List<GroupSession> groups;

    @Override
    public void bind(String username, Channel channel) {
        List<Session> clients = Optional.ofNullable(this.clients)
                .orElseGet(ArrayList::new);
        this.clients = clients;
        clients.add(new Session(username, channel));
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
            if (sessionUsername.equals(username)) {
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
    public void groupCreate(String groupOwner, String groupName, String... members) {
        GroupSession groupSession = new GroupSession();
        groupSession.setGroupName(groupName);
        groupSession.setGroupOwner(groupOwner);
        groupSession.setMembers(Arrays.stream(members)
                .map(this::getSession)
                .collect(Collectors.toList()));
    }

    @Override
    public void groupAdd(String groupName, Session session) {
        List<GroupSession> groups = Optional.ofNullable(this.groups)
                .orElseGet(ArrayList::new);
        this.groups = groups;
        for (GroupSession group : groups) {
            String groupNameTemp = group.getGroupName();
            if (!groupNameTemp.equals(groupName)) {
                continue;
            }
            group.join(session);
            return;
        }
        groupCreate(session.getUsername(), groupName);
    }

    @Override
    public void groupAdd(String groupName, String username) {
        Session session = getSession(username);
        if (session == null) {
            return;
        }
        groupAdd(groupName, session);
    }


}
