package org.mytest.test.session.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author gemo
 * @date 2022/4/17 20:59
 **/
@Data
@AllArgsConstructor
public class User {
    private String id;
    private String name;
    private String password;
}
