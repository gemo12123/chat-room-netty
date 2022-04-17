package org.mytest.test.session.service.impl;

import cn.hutool.core.util.StrUtil;
import org.mytest.test.message.impl.LoginResponseMessage;
import org.mytest.test.session.entity.User;
import org.mytest.test.session.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author gemo
 * @date 2022/4/17 17:15
 **/
public class MemoryUserServiceImpl implements UserService {
    public static Map<String, User> user = new HashMap<>();

    static {
        user.put("zhangsan", new User("d89e7f7b-9e84-4575-9e1a-b811220234b1", "zhangsan", "12123"));
        user.put("lisi", new User("8fca5b66-7381-4fca-a955-10259088150e", "lisi", "12123"));
        user.put("wangwu", new User("42f0abfb-4445-4ab0-b9a7-b67f631b42cf", "wangwu", "12123"));
        user.put("zhaoliu", new User("fe05f703-845f-4d2c-bfb9-92b2c0873323", "zhaoliu", "12123"));
        user.put("sunqi", new User("fe05f703-845f-4d2c-bfb9-92b2c0873323", "sunqi", "12123"));
    }

    @Override
    public boolean login(String username, String password) {
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
            return false;
        }
        if (!user.containsKey(username)) {
            return false;
        }
        User user = MemoryUserServiceImpl.user.get(username);
        return user.getPassword().equals(password);
    }
}
