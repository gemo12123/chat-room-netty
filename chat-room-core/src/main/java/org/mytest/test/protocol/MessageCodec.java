package org.mytest.test.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.mytest.test.message.Message;
import org.mytest.test.serializer.SerializationProcessor;
import org.mytest.test.serializer.Serializer;

import java.util.List;

/**
 * 编解码器（可共享）
 *
 * @author gemo
 * @date 2022/4/14 19:22
 **/
@ChannelHandler.Sharable
public class MessageCodec extends MessageToMessageCodec<ByteBuf, Message> {
    private static final byte[] MAGIC_NUMBER = {'g', 'e', 'm', 'o'};

    private Serializer serializer;

    public MessageCodec() {
        this(Serializer.DEFAULT_SERIALIZER);
    }

    public MessageCodec(Serializer serializer) {
        this.serializer = serializer;
    }

    /**
     * 编码器
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        // 魔数，4字节
        buffer.writeBytes(MAGIC_NUMBER);
        // 版本号，1字节
        buffer.writeByte(1);
        // 序列化器类型，1字节
        buffer.writeByte(serializer.getType());
        // 指令类型，1字节
        buffer.writeByte(msg.getInstructionType());
        // 请求序号，4字节
        buffer.writeByte(msg.getSequenceId());
        // 序列化消息
        SerializationProcessor serializationProcessor = serializer.getSerializationProcessor();
        byte[] msgByte = serializationProcessor.serialize(msg);
        // 正文长度，4字节
        buffer.writeInt(msgByte.length);
        // 对其填充
        buffer.writeByte(0xff);
        // 消息正文
        buffer.writeBytes(msgByte);

        out.add(buffer);
    }

    /**
     * 解码器
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        ByteBuf magicNum = msg.writeInt(4);
//        ByteBuf byteBuf = msg.writeByte(1);
    }
}
