package com.zhy.provider.service;

import com.zhy.spi.UserServiceDistributor;
import com.zhy.spi.UserServiceFactory;
import com.zhy.spi.UserServiceType;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhouhongyin
 * @since 2024/3/25 16:59
 */
@DubboService
public class UserServiceDistributorImpl implements UserServiceDistributor {

    @Autowired
    private UserServiceFactory userServiceFactory;

    @Override
    public String getUser(String name, UserServiceType userServiceType) {
        return userServiceFactory.getService(userServiceType).getUser(name);
    }
}
