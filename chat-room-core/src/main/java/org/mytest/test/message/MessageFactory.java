package org.mytest.test.message;

import org.mytest.test.message.impl.LoginRequestMessage;
import org.mytest.test.message.impl.PingMessage;
import org.mytest.test.message.impl.PongMessage;

/**
 * @author gemo
 * @date 2022/4/16 20:33
 **/
public class MessageFactory {

    public static Message buildMessage(byte msgType,byte[] msg){
        return null;
    }

    public static Message buildMessage(byte msgType,int sequenceId,byte[] msg){
        switch (msgType){
            case 0:
                return new PingMessage();
            case 1:
                return new PongMessage();
            case 2:
//                return new LoginRequestMessage()
        }
        return null;
    }
}
