package com.zhy.provider.service;

import com.zhy.spi.UserService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author zhouhongyin
 * @since 2023/5/14 21:08
 */
@DubboService
public class UserServiceImpl implements UserService {

    @Override
    public String getUser(String name) {
        return "I'm provider receive message: " + name;
    }
}
