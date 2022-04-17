package org.mytest.test.session.service;

/**
 * 基于内存的用户服务，用户服务
 *
 * @author gemo
 * @date 2022/4/17 17:12
 **/
public interface UserService {
    /**
     * 判断用户名密码是否正确
     *
     * @param username
     * @param password
     * @return
     */
    boolean login(String username,String password);
}