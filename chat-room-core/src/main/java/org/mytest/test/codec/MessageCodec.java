package org.mytest.test.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import org.mytest.test.message.Message;
import org.mytest.test.protocol.Protocol;
import org.mytest.test.protocol.ProtocolFactory;

import java.util.List;

/**
 * 编解码器（可共享）
 *
 * @author gemo
 * @date 2022/4/14 19:22
 **/
@Slf4j
@ChannelHandler.Sharable
public class MessageCodec extends MessageToMessageCodec<ByteBuf, Message> {
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
        Protocol protocol = ProtocolFactory.buildDefaultProtocol(msg);
        protocol.writeToByteBuf(buffer);
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
        Protocol protocol = ProtocolFactory.parse(msg);
        Message message = protocol.getMessage();
        out.add(message);
    }
}
