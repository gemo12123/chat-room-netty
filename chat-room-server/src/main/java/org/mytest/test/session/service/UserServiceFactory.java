package org.mytest.test.session.service;

import org.mytest.test.session.service.impl.MemoryUserServiceImpl;

/**
 * @author gemo
 * @date 2022/4/17 17:20
 **/
public class UserServiceFactory {
    public static UserService getUserService(){
        return new MemoryUserServiceImpl();
    }
}
