package com.zhy.provider.manual;

import com.zhy.provider.service.UserServiceImpl3;
import com.zhy.spi.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;

/**
 * 手动注册 provider
 *
 * @author zhouhongyin
 * @since 2023/5/16 17:21
 */
public class RegisterProvider {

    public static void main(String[] args) throws IOException {
        ApplicationConfig applicationConfig = new ApplicationConfig("dubbo-provider");

        RegistryConfig registryConfig = new RegistryConfig("zookeeper://zhouhongyin.top:21810");

        ServiceConfig<UserService> serviceConfig = new ServiceConfig<>();

        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setInterface(UserService.class);
        serviceConfig.setRef(new UserServiceImpl3());

        System.in.read();

    }

}
