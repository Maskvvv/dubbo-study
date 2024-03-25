package com.zhy.provider.service;

import com.zhy.spi.UserService;
import com.zhy.spi.UserServiceFactory;
import com.zhy.spi.UserServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouhongyin
 * @since 2024/3/25 16:42
 */
@Service
public class UserServiceFactoryImpl implements UserServiceFactory {

    private Map<UserServiceType, UserService> userServiceMap;

    @Autowired
    public UserServiceFactoryImpl(List<UserService> userServices) {
        userServiceMap = new HashMap<>();

        for (UserService userService : userServices) {
            userServiceMap.put(userService.getType(), userService);
        }
    }

    @Override
    public UserService getService(UserServiceType userServiceType) {
        return userServiceMap.get(userServiceType);
    }
}
