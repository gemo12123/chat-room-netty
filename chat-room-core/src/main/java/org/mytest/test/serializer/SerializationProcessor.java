package org.mytest.test.serializer;

import io.netty.buffer.ByteBuf;
import org.mytest.test.message.Message;

/**
 * 序列化处理器，用于提供序列化和反序列化功能
 *
 * @author gemo
 * @date 2022/4/14 19:47
 **/
public interface SerializationProcessor {

    byte[] serialize(Message msg);

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    Message deserialize(byte[] bytes);


}
