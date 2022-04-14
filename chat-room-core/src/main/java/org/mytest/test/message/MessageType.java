package org.mytest.test.message;

/**
 * @author gemo
 * @date 2022/4/14 20:22
 **/
public enum MessageType {

    PING(0),
    PONG(1),
    LOGIN_REQUEST(2),
    LOGOUT_RESPONSE(3),
    CHAT_REQUEST(4),
    CHAT_RESPONSE(5),
    GROUP_CREATE_REQUEST(6),
    GROUP_CREATE_RESPONSE(7),
    GROUP_CHAT_REQUEST(8),
    GROUP_CHAT_RESPONSE(9);

    private int type;

    MessageType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
