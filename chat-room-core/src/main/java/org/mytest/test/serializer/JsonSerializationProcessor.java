package org.mytest.test.serializer;

import org.mytest.test.message.Message;

/**
 * @author gemo
 * @date 2022/4/14 19:48
 **/
public class JsonSerializationProcessor implements SerializationProcessor {
    @Override
    public byte[] serialize(Message msg) {
        return new byte[0];
    }

    @Override
    public Message deserialize(byte[] bytes) {
        return null;
    }
}
