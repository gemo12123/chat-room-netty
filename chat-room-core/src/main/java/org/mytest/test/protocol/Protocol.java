package org.mytest.test.protocol;

import org.mytest.test.message.Message;
import org.mytest.test.protocol.version.Version;
import org.mytest.test.serializer.Serializer;

/**
 * 协议，16字节头信息+消息正文：
 * 魔数，4字节
 * 版本号，1字节
 * 序列化器类型，1字节
 * 指令类型，1字节
 * 请求序号，4字节
 * 正文长度，4字节
 * 保留字段，1字节，v1中是对其填充
 * 消息正文
 *
 * @author lcq
 * @date 2022/4/14 21:28
 **/
public class Protocol {
    public static final byte[] MAGIC_NUMBER = {'g','e','m','o'};
    private Version version;
    private Serializer serializer;
    private Message message;
}
