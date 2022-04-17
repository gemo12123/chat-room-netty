package org.mytest.test.message;

/**
 * @author gemo
 * @date 2022/4/14 20:22
 **/
public enum MessageType {

    /**
     * 心跳消息
     */
    PING((byte)0),
    PONG((byte)1),
    /**
     * 登陆请求响应
     */
    LOGIN_REQUEST((byte)2),
    LOGIN_RESPONSE((byte)3),
    /**ZS
     * 私聊请求响应
     */
    CHAT_REQUEST((byte)4),
    CHAT_RESPONSE((byte)5),
    /**
     * 创建群聊请求响应
     */
    GROUP_CREATE_REQUEST((byte)6),
    GROUP_CREATE_RESPONSE((byte)7),
    /**
     * 群聊请求响应
     */
    GROUP_CHAT_REQUEST((byte)8),
    GROUP_CHAT_RESPONSE((byte)9),
    /**
     * 群聊退出请求响应
     */
    GROUP_QUIT_REQUEST((byte)10),
    GROUP_QUIT_RESPONSE((byte)11);

    private byte type;


    MessageType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }
}
