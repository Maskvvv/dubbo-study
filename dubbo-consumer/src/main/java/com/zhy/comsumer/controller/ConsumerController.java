package com.zhy.comsumer.controller;

import com.zhy.spi.UserService;
import com.zhy.spi.UserServiceDistributor;
import com.zhy.spi.UserServiceType;
import org.apache.dubbo.common.constants.LoadbalanceRules;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhouhongyin
 * @since 2023/5/14 21:22
 */
@RestController
@RequestMapping("consumer")
public class ConsumerController {

    @DubboReference(
            check = false,
            loadbalance = LoadbalanceRules.ROUND_ROBIN,
            group = "impl2"
    )
    private UserService userServiceImpl1;

    @DubboReference(check = false)
    private UserServiceDistributor userServiceDistributor;

    //@DubboReference(check = false)
    //private List<UserService> userServices;

    @GetMapping("test")
    private String test() {
        String res = userServiceImpl1.getUser("mike");
        System.out.println(res);
        return res;
    }

    @GetMapping("test1")
    private String test1() {
        String res = userServiceDistributor.getUser("zhy", UserServiceType.TYPE1);
        System.out.println(res);
        return res;
    }

}
