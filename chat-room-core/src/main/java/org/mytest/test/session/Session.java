package org.mytest.test.session;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author gemo
 * @date 2022/4/24 20:25
 **/
@Data
@AllArgsConstructor
public class Session {
    private String username;
    private Channel channel;
}
