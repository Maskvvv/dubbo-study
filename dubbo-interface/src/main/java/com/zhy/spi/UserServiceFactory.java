package com.zhy.spi;

/**
 * @author zhouhongyin
 * @since 2024/3/25 16:41
 */

public interface UserServiceFactory {

    UserService getService(UserServiceType userServiceType);

}
