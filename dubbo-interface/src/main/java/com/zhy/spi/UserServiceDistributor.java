package com.zhy.spi;

/**
 * @author zhouhongyin
 * @since 2024/3/25 16:41
 */

public interface UserServiceDistributor {

    String getUser(String name, UserServiceType userServiceType);

}
