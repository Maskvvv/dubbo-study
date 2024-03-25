package com.zhy.comsumer.service;

import com.zhy.spi.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouhongyin
 * @since 2023/5/14 21:22
 */
//@Component
public class Consumer {

    public final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1, r -> {
        Thread thread = new Thread(r);
        thread.setName("Consumer-scheduledThread-");
        return thread;
    });

    @DubboReference(
            /** 接口调研超时时间，1毫秒 **/
            timeout = 10000,
            /** 启动时不检查 DemoFacade 是否能正常提供服务 **/
            check = false,

            /** 为 DemoFacade 的 sayHello 方法设置事件通知机制 **/
            methods = {@Method(
                    name = "getUser",
                    oninvoke = "eventNotifyService.onInvoke",
                    onreturn = "eventNotifyService.onReturn",
                    onthrow = "eventNotifyService.onThrow")}
    )
    private UserService userService;

    @PostConstruct
    private void init() {
        System.out.println("init-consumer");

        System.out.println(userService.getUser("111111111"));

        executorService.scheduleWithFixedDelay(() -> {
            String user = userService.getUser(String.valueOf(System.currentTimeMillis()));
            System.out.println(Thread.currentThread().getName());
            System.out.println(user);

            System.out.println("----------------------------------");
        }, 1, 3, TimeUnit.SECONDS);

    }

}
