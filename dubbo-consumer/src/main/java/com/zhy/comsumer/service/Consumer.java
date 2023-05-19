package com.zhy.comsumer.service;

import com.zhy.spi.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouhongyin
 * @since 2023/5/14 21:22
 */
@Component
public class Consumer {

    @DubboReference
    private UserService userService;

    @PostConstruct
    private void init() {
        System.out.println("init-consumer");

        System.out.println(userService.getUser("111111111"));

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1, r -> {
            Thread thread = new Thread(r);
            thread.setName("Consumer-scheduledThread-");
            return thread;
        });

        scheduledThreadPool.scheduleWithFixedDelay(() -> {
            String user = userService.getUser(String.valueOf(System.currentTimeMillis()));
            System.out.println(Thread.currentThread().getName());
            System.out.println(user);

            System.out.println("----------------------------------");
        }, 1, 3, TimeUnit.SECONDS);

    }

}
