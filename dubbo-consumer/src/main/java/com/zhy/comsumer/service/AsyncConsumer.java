package com.zhy.comsumer.service;

import com.zhy.spi.AsyncUserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 服务提供方异步化实践
 *
 * @author zhouhongyin
 * @since 2023/5/14 21:22
 */
@Component
public class AsyncConsumer {

    @DubboReference
    private AsyncUserService userService;

    //@PostConstruct
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

    //@PostConstruct
    private void future() {
        CompletableFuture<String> userFuture = userService.getUserFuture(String.valueOf(System.currentTimeMillis()));
        userFuture.whenComplete((value, e) -> {

            System.out.println("AsyncConsumer-future");
            System.out.println(value);
        });

        System.out.println("end");
    }

}
