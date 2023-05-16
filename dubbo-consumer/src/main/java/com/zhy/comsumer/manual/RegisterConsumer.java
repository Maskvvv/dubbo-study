package com.zhy.comsumer.manual;

import com.zhy.spi.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

import java.io.IOException;

/**
 * 手动注册 consumer
 *
 * @author zhouhongyin
 * @since 2023/5/16 17:21
 */
public class RegisterConsumer {

    public static void main(String[] args) throws IOException {
        ApplicationConfig applicationConfig = new ApplicationConfig("dubbo-provider");

        RegistryConfig registryConfig = new RegistryConfig("zookeeper://zhouhongyin.top:21810");

        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(UserService.class);

        UserService userService = referenceConfig.get();
        String user = userService.getUser("hahaha");

        System.in.read();

    }

}
