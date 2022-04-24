package org.mytest.test.manager;

import lombok.Data;

/**
 * client管理器，记录了client端相关信息
 *
 * @author gemo
 * @date 2022/4/24 20:03
 **/
@Data
public class ClientManagerImpl implements ClientManager{
    private String username;

    @Override
    public void register(String username) {
        if (this.username == null) {
            synchronized (this) {
                if (this.username == null) {
                    this.username = username;
                }
            }
        }
    }

    @Override
    public void exist() {
        synchronized (this) {
            this.username = null;
        }
    }
}
