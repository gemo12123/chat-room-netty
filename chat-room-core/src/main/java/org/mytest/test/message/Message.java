package org.mytest.test.message;

import java.io.Serializable;

/**
 * 消息父类
 *
 * @author gemo
 * @date 2022/4/14 19:26
 **/
public class Message implements Serializable {
    protected MessageType messageType;
    protected int sequenceId;

    public Message() {
    }

    protected Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getInstructionType() {
        return messageType.getType();
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }
}
