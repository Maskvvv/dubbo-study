package com.zhy.spi;

import java.util.concurrent.CompletableFuture;

/**
 * @author zhouhongyin
 * @since 2023/5/14 21:06
 */
public interface AsyncUserService {

    String getUser(String name);


    CompletableFuture<String> getUserFuture(String name);

}
