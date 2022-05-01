package org.mytest.test.protocol;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.message.Message;
import org.mytest.test.protocol.version.Version;
import org.mytest.test.serializer.SerializationProcessor;
import org.mytest.test.serializer.Serializer;

/**
 * 协议，16字节头信息+消息正文：
 * 魔数，4字节
 * 版本号，1字节
 * 序列化器类型，1字节
 * 指令类型，1字节
 * 正文长度，4字节
 * 对齐填充，5字节，各版本版本意义可能不同
 * 消息正文
 *
 * @author gemo
 * @date 2022/4/14 21:28
 **/
@Data
@Slf4j
public abstract class Protocol {
    public static final byte[] MAGIC_NUMBER = {'g', 'e', 'm', 'o'};
    private Version version;
    protected Serializer serializer;
    protected byte[] serializerMessage;
    protected Message message;

    public Protocol(Version version, Serializer serializer, byte[] serializerMessage) {
        this.version = version;
        this.serializer = serializer;
        this.serializerMessage = serializerMessage;
        this.message = serializer.getSerializationProcessor()
                .deserialize(serializerMessage);
    }

    public Protocol(Version version, Serializer serializer, Message message) {
        this.version = version;
        this.serializer = serializer;
        this.message = message;
        this.serializerMessage = serializer.getSerializationProcessor()
                .serialize(message);
    }

    /**
     * 对其填充
     */
    public abstract void alignPadding(ByteBuf buffer);

    public void writeToByteBuf(ByteBuf buffer) {
        // 魔数，4字节
        buffer.writeBytes(MAGIC_NUMBER);
        // 版本号，1字节
        buffer.writeByte(version.getVersion());
        // 序列化器类型，1字节
        buffer.writeByte(serializer.getType());
        // 指令类型，1字节
        buffer.writeByte(message.getInstructionType());
        // 正文长度，4字节
        buffer.writeInt(serializerMessage.length);
        // 对其填充，5字节
        alignPadding(buffer);
        // 消息正文
        buffer.writeBytes(serializerMessage);

    }
}
