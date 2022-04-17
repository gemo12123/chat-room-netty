package org.mytest.test.exception;

/**
 * @author gemo
 * @date 2022/4/16 21:45
 **/
public class ProtocolCheckException extends Exception {
    public ProtocolCheckException() {
        super("协议校验出错！");
    }
}
