package org.mytest.test.protocol;

import io.netty.buffer.ByteBuf;
import org.mytest.test.constant.Constant;
import org.mytest.test.message.Message;
import org.mytest.test.protocol.version.Version;
import org.mytest.test.serializer.Serializer;

/**
 * 对其填充为4字节序列号 + 1字节对齐填充（0xff）
 *
 * @author gemo
 * @date 2022/4/16 21:10
 **/
public class Protocol2 extends Protocol {

    public Protocol2(Serializer serializer, byte[] serializerMessage) {
        super(Version.VERSION_2, serializer, serializerMessage);
    }

    public Protocol2(Serializer serializer, Message message) {
        super(Version.VERSION_2, serializer, message);
    }

    @Override
    public void alignPadding(ByteBuf buffer) {
        buffer.writeInt(message.getSequenceId());
        buffer.writeByte(Constant.ALIGN_PADDING);
    }
}
