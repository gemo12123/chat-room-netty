package org.mytest.test.protocol;

import io.netty.buffer.ByteBuf;
import org.mytest.test.constant.Constant;
import org.mytest.test.message.Message;
import org.mytest.test.protocol.version.Version;
import org.mytest.test.serializer.Serializer;

/**
 * 对齐填充，为5字节的0xff，无实际意义
 *
 * @author gemo
 * @date 2022/4/16 21:06
 **/
public class Protocol1 extends Protocol {

    public Protocol1(Serializer serializer, byte[] serializerMessage) {
        super(Version.VERSION_1, serializer, serializerMessage);

    }

    public Protocol1(Serializer serializer, Message message) {
        super(Version.VERSION_1, serializer, message);
    }

    @Override
    public void alignPadding(ByteBuf buffer) {
        buffer.writeByte(Constant.ALIGN_PADDING);
        buffer.writeByte(Constant.ALIGN_PADDING);
        buffer.writeByte(Constant.ALIGN_PADDING);
        buffer.writeByte(Constant.ALIGN_PADDING);
        buffer.writeByte(Constant.ALIGN_PADDING);
    }
}
