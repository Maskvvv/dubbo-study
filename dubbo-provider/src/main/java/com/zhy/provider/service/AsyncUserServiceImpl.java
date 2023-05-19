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
 * 异步调用
 * 1. 通过 AsyncContext#startAsync 实现提供方
 * 2. 通过返回 CompletableFuture 实现提供方
 *
 * https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/advanced-features-and-usage/service/async-call/
 *
 *
 * 理解异步化有个前提，就是要区分两个线程：请求处理主线程&业务异步子线程：
 * - 请求处理主线程：由 Dubbo 框架提供，主要用于接收 RPC 请求。线程池大小默认为200。
 * - 业务异步子线程：由业务自定义，可设置线程池大小、队列长度、拒绝策略等，用于异步执行业务逻辑。
 * 异步化的核心思想在于，将本来需要由主线程来执行的耗时操作，交给异步子线程来执行，使得主线程可以快速执行完成，避免 Dubbo 线程池被耗尽导致服务不可用。站在调用方的角度来看，实际请求的执行时间并没有缩短，但是服务整体的吞吐量是有很大的提升的。
 *
 * @author zhouhongyin
 * @since 2023/5/14 21:08
 */
@DubboService
public class AsyncUserServiceImpl implements AsyncUserService {

    @Value("${server.port:8080}")
    private String port;

    private final ExecutorService threadPool = Executors.newFixedThreadPool(1);


    /**
     * CompletableFuture 的方式
     * 通过 return CompletableFuture.supplyAsync() ，业务执行已从 Dubbo 线程切换到业务线程，避免了对 Dubbo 线程池的阻塞。
     */
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

    /**
     * AsyncContext 的方式
     */
    @Override
    public String getUser(String name) {
        String message = "I'm asynchronous provider(" + port + ") receive message: " + name;

        AsyncContext asyncContext = RpcContext.startAsync();

        threadPool.execute(() -> {
            // 如果要使用上下文，则必须要放在第一句执行
            asyncContext.signalContextSwitch();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 写回响应
            asyncContext.write(message);

        });
        return null;
    }

}
