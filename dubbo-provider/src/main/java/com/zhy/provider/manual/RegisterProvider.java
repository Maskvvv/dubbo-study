package com.zhy.provider.manual;

import com.zhy.provider.service.UserServiceImpl;
import com.zhy.spi.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
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
        serviceConfig.setRef(new UserServiceImpl());

        System.in.read();

    }

}
