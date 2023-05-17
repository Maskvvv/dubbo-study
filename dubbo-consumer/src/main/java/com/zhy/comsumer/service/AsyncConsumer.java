package com.zhy.comsumer.service;

import com.zhy.spi.AsyncUserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 异步调用
 *
 * @author zhouhongyin
 * @since 2023/5/14 21:22
 */
@Component
public class AsyncConsumer {

    @DubboReference
    private AsyncUserService userService;


    /**
     * CompletableFuture 的方式
     */
    private void future() {
        CompletableFuture<String> userFuture = userService.getUserFuture(String.valueOf(System.currentTimeMillis()));
        userFuture.whenComplete((v, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println("Response: " + v);
            }
        });

        System.out.println("end");
    }


    /**
     * AsyncContext 的方式一
     */
    private void context() {
        // 此调用会立即返回null
        userService.getUser("world");
        // 拿到调用的Future引用，当结果返回后，会被通知和设置到此Future
        CompletableFuture<String> helloFuture = RpcContext.getServiceContext().getCompletableFuture();
        // 为Future添加回调
        helloFuture.whenComplete((retValue, exception) -> {
            if (exception == null) {
                System.out.println(retValue);
            } else {
                exception.printStackTrace();
            }
        });

    }

    /**
     * AsyncContext 的方式二
     */
    private void context1() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future = RpcContext.getServiceContext().asyncCall(() -> {
                return userService.getUser("oneway call request1");
            });

        future.get();
    }

}
