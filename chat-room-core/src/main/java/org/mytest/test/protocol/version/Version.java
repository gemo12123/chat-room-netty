package org.mytest.test.protocol.version;

/**
 * 协议版本父抽象类
 *
 * @author gemo
 * @date 2022/4/14 21:07
 **/
public abstract class Version {
    private static final byte[] MAGIC_NUMBER = {'g','e','m','o'};
    public byte version;

    protected Version(byte version){
        this.version=version;
    }

    public static byte[] getMagicNumber() {
        return MAGIC_NUMBER;
    }

    public byte getVersion() {
        return version;
    }
}
