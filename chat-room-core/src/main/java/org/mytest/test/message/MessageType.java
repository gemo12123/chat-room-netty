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
    GROUP_QUIT_RESPONSE((byte)11),
    PERSON_QUIT_REQUEST((byte)12),
    PERSON_QUIT_RESPONSE((byte)13),
    /**
     * 群组加入请求响应
     */
    GROUP_JOIN_REQUEST((byte)14),
    GROUP_JOIN_RESPONSE((byte)15),
    /**
     * 获取群组
     */
    GROUP_GET_REQUEST((byte)16),
    GROUP_GET_RESPONSE((byte)17),
    /**
     * 获取群组成员
     */
    GROUP_MEMBER_REQUEST((byte)18),
    GROUP_MEMBER_RESPONSE((byte)19);

    private byte type;


    MessageType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }
}
