package org.mytest.test.serializer;

/**
 * @author gemo
 * @date 2022/4/14 19:52
 **/
public enum Serializer {

    /**
     * 默认的序列化器
     */
    DEFAULT_SERIALIZER(0, new DefaultSerializationProcess()),
    /**
     * 流式序列化器
     */
    STREAM_SERIALIZER(1, new StreamSerializationProcessor()),
    /**
     * JSON序列化器
     */
    JSON_SERIALIZER(2, new JsonSerializationProcessor());

    private int type;
    private SerializationProcessor serializationProcessor;

    Serializer(int type, SerializationProcessor serializationProcessor) {
        this.type = type;
        this.serializationProcessor = serializationProcessor;
    }

    public int getType() {
        return type;
    }

    public SerializationProcessor getSerializationProcessor() {
        return serializationProcessor;
    }
}
