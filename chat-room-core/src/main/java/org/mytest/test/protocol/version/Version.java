package org.mytest.test.protocol.version;

/**
 * 协议版本
 *
 * @author gemo
 * @date 2022/4/14 21:07
 **/
public enum Version {
    /**
     * 版本一
     * 版本号：1
     * 扩展字段：5字节对齐填充（0xff）
     */
    VERSION_1((byte)1),
    /**
     * 版本一
     * 版本号：2
     * 扩展字段：4字节序列号 + 1字节对齐填充（0xff）
     */
    VERSION_2((byte)2);

    private byte version;


    Version(byte version) {
        this.version = version;
    }

    public byte getVersion() {
        return version;
    }
}
