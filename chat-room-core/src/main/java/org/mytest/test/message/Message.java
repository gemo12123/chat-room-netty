package org.mytest.test.message;

/**
 * 消息父类
 *
 * @author gemo
 * @date 2022/4/14 19:26
 **/
public class Message {
    protected MessageType messageType;
    protected int sequenceId;

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
