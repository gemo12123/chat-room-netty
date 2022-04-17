package org.mytest.test.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 帧解码器
 *
 * @author gemo
 * @date 2022/4/17 11:25
 **/
public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {

    public ProtocolFrameDecoder(){
        super(1024*10,7,4,5,0);
    }
}
