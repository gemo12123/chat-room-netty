package org.mytest.test.manager;

import org.mytest.test.session.Session;

import io.netty.channel.Channel;

/**
 * @author gemo
 * @date 2022/4/24 20:18
 **/
public interface ServerManager extends Manager {

    /**
     * 用户绑定channel
     *
     * @param username
     * @param channel
     */
    void bind(String username, Channel channel);

    /**
     * 用户解绑
     *
     * @param username
     */
    void unbind(String username);

    /**
     * 根据用户名获取channel
     *
     * @param username
     * @return
     */
    Session getSession(String username);
    /**
     * 根据用户名获取channel
     *
     * @param username
     * @return
     */
    Channel getChannel(String username);


    /**
     * 创建群组
     *
     * @param groupOwner
     * @param groupName
     * @param members
     */
    void groupCreate(String groupOwner, String groupName, String... members);

    /**
     * 群组添加成员channel
     *
     * @param groupName
     * @param session
     */
    void groupAdd(String groupName, Session session);

    /**
     * 群组绑定channel
     *
     * @param groupName
     * @param username
     */
    void groupAdd(String groupName, String username);
}
