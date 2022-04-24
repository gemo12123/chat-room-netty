package org.mytest.test.manager;

/**
 * @author gemo
 * @date 2022/4/24 20:18
 **/
public interface ClientManager extends Manager {
    /**
     * 注册用户
     * @param username
     */
    void register(String username);

    /**
     * 用户退出
     */
    void exist();
}
