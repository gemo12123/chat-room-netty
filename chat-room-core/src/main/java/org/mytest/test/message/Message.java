package org.mytest.test.message;

/**
 * @author gemo
 * @date 2022/4/14 19:26
 **/
public abstract class Message {
    protected MessageType messageType;
    protected int sequenceId;
    protected String content;

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
