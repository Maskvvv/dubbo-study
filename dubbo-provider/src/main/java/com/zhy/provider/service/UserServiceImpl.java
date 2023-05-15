package com.zhy.provider.service;

import com.zhy.spi.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author zhouhongyin
 * @since 2023/5/14 21:08
 */
@DubboService
public class UserServiceImpl implements UserService {

    @Value("${server.port}")
    private String port;

    @Override
    public String getUser(String name) {
        return "I'm provider(" + port + ") receive message: " + name;
    }
}
