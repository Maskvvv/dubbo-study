package com.zhy.provider.service;

import com.zhy.spi.AsyncUserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务提供方异步化实践
 * 1. 通过 AsyncContext#startAsync 实现提供方
 * 2. 通过返回 CompletableFuture 实现提供方
 *
 * @author zhouhongyin
 * @since 2023/5/14 21:08
 */
@DubboService
public class AsyncUserServiceImpl implements AsyncUserService {

    @Value("${server.port:8080}")
    private String port;

    private final ExecutorService threadPool = Executors.newFixedThreadPool(1);


    @Override
    public String getUser(String name) {
        String message = "I'm provider(" + port + ") receive message: " + name;

        AsyncContext asyncContext = RpcContext.startAsync();

        threadPool.execute(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            asyncContext.write(message);

        });



        return null;
    }

    @Override
    public CompletableFuture<String> getUserFuture(String name) {
        String message = "I'm provider(" + port + ") receive message: " + name;


        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return message;
        });

    }
}
