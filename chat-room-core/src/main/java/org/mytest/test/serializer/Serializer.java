package org.mytest.test.serializer;

/**
 * @author gemo
 * @date 2022/4/14 19:52
 **/
public enum Serializer {

    /**
     * 默认的序列化器
     */
    DEFAULT_SERIALIZER((byte)0, new DefaultSerializationProcess()),
    /**
     * 流式序列化器
     */
    STREAM_SERIALIZER((byte)1, new StreamSerializationProcessor()),
    /**
     * JSON序列化器
     */
    JSON_SERIALIZER((byte)2, new JsonSerializationProcessor());

    private byte type;
    private SerializationProcessor serializationProcessor;

    Serializer(byte type, SerializationProcessor serializationProcessor) {
        this.type = type;
        this.serializationProcessor = serializationProcessor;
    }

    public byte getType() {
        return type;
    }

    public SerializationProcessor getSerializationProcessor() {
        return serializationProcessor;
    }

    public static Serializer parse(byte type){
        switch (type){
            case 1:
                return STREAM_SERIALIZER;
            case 2:
                return JSON_SERIALIZER;
            default:
                return DEFAULT_SERIALIZER;
        }
    }
}
