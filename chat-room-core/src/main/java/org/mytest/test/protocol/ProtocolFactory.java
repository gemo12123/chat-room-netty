package org.mytest.test.protocol;

import io.netty.buffer.ByteBuf;
import org.mytest.test.exception.ProtocolCheckException;
import org.mytest.test.message.Message;
import org.mytest.test.message.MessageFactory;
import org.mytest.test.serializer.Serializer;
import org.mytest.test.protocol.version.Version;

import java.util.Arrays;

/**
 * @author gemo
 * @date 2022/4/16 19:57
 **/
public class ProtocolFactory {

    public static Protocol buildDefaultProtocol(Message message) {
        return new Protocol1(Serializer.DEFAULT_SERIALIZER, message);
    }

    public static Protocol parse(ByteBuf buffer) throws ProtocolCheckException {
        // 魔数
        byte[] magicNum = new byte[4];
        buffer.readBytes(magicNum);
        // 协议版本
        byte protocolVersion = buffer.readByte();
        // 序列化器版本
        byte serializerType = buffer.readByte();
        // 消息类型
        byte messageType = buffer.readByte();
        // 消息长度
        int messageLength = buffer.readInt();
        // 对其填充
        // TODO: 2022/4/16 直接按照协议二读取
        int sequenceId = buffer.readInt();
        byte alignPadding = buffer.readByte();
        // 消息正文
        byte[] message = new byte[messageLength];
        buffer.readBytes(message);

        if (!Arrays.equals(Protocol.MAGIC_NUMBER, magicNum)) {
            throw new ProtocolCheckException();
        }

        return ProtocolFactory.parseProtocol(protocolVersion,
                messageType,
                serializerType,
                sequenceId,
                message
                );
    }

    private static Protocol parseProtocol(byte protocolVersion, byte messageType, byte serializerType, int sequenceId, byte[] message) {
        Serializer serializer = Serializer.parse(serializerType);
        switch (protocolVersion) {
            case 1:
                return new Protocol1(serializer, message);
            case 2:
                return new Protocol2(serializer, message);
            default:
                return new Protocol1(serializer, message);
        }
    }
}
