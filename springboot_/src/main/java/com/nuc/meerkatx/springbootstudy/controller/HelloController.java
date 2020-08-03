package com.nuc.meerkatx.springbootstudy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/11
 * @Description: 一个基本的helloController
 */
//@AsyncController
@Controller
@Slf4j
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    public RedisTemplate redisTemplate;//调用redis

    @RequestMapping("/h1")
    public String hello(Model model) {
        log.info("====h1 is running====");
        return "/index.html";
    }
}
