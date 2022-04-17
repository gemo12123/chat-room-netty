package org.mytest.test.session.group;

import java.nio.channels.Channel;

/**
 * @author gemo
 * @date 2022/4/17 21:10
 **/
public interface Session {
    /**
     * 绑定会话
     * @param username
     * @param channel
     */
    void bind(String username,Channel channel);

    /**
     * 解绑会话
     * @param username
     */
    void unbind(String username);

    /**
     * 根据用户名获取Channel
     * @param username
     * @return
     */
    Channel getChannel(String username);
}
