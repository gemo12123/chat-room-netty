package org.mytest.test.util;

/**
 * @author gemo
 * @date 2022/5/1 10:53
 **/
public class ClientUtils {
    public static void printSystemResponse(boolean success,String content){
        if (success) {
            System.out.println(content);
        }else {
            System.err.println(content);
        }
    }
}
