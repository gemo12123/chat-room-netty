package org.mytest.test.serializer;

import lombok.extern.slf4j.Slf4j;
import org.mytest.test.message.Message;

import java.io.*;

/**
 * @author gemo
 * @date 2022/4/14 19:48
 **/
@Slf4j
public class StreamSerializationProcessor implements SerializationProcessor {
    @Override
    public byte[] serialize(Message msg) {
        ByteArrayOutputStream bos=null;
        ObjectOutputStream oos=null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(msg);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("序列化失败", e);
        }finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Message deserialize(byte[] bytes) {
        ObjectInputStream ois=null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return (Message)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("反序列化失败", e);
        }finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
