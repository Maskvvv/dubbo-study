package com.zhy.provider.service;

import com.zhy.spi.UserService;
import com.zhy.spi.UserServiceType;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zhouhongyin
 * @since 2023/5/14 21:08
 */
@Service
//@DubboService
@DubboService(version = "v1" ,group = "impl2")
public class UserServiceImpl2 implements UserService {

    @Value("${server.port:8080}")
    private String port;

    @Override
    public String getUser(String name) {
        return "service 2 I'm provider(" + port + ") receive message: " + name;
    }

    @Override
    public UserServiceType getType() {
        return UserServiceType.TYPE2;
    }
}
