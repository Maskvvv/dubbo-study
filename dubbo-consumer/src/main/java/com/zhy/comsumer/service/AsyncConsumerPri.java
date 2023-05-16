package com.zhy.comsumer.service;

import com.zhy.spi.AsyncUserService;
import com.zhy.spi.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

/**
 * 服务消费方异步化实践
 *
 * 1. 通过 getFuture 方式从 provider 拿到结果
 * 2. 通过 setCallback 方式从 provider 拿到结果
 *
 * @author zhouhongyin
 * @since 2023/5/14 21:22
 */
@Component
public class AsyncConsumerPri {

    @DubboReference(async = true)
    private UserService userService;

    @DubboReference(async = true)
    private AsyncUserService asyncUserService;

    /**
     * 1. 通过 getFuture 方式从 provider 拿到结果
     */
    //@PostConstruct
    private void getFuture() throws ExecutionException, InterruptedException {

        String user = userService.getUser(String.valueOf(System.currentTimeMillis()));
        System.out.println(user);
        System.out.println(RpcContext.getContext().getFuture().get());


        String user1 = asyncUserService.getUser(String.valueOf(System.currentTimeMillis()));
        System.out.println(user1);
        System.out.println(RpcContext.getContext().getFuture().get());

    }

    /**
     * 2. 通过 setCallback 方式从 provider 拿到结果
     */
    @PostConstruct
    private void setCallback() {

        String user = userService.getUser(String.valueOf(System.currentTimeMillis()));
        System.out.println(user);

        RpcContext.getServiceContext().getCompletableFuture().thenAccept(System.out::println);

    }

}
