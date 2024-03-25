package com.zhy.comsumer.controller;

import com.zhy.spi.UserService;
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
            check = false
    )
    private UserService userService;

    @GetMapping("test")
    private String init() {
        String res = userService.getUser("mike");

        System.out.println(res);

        return res;
    }

}
