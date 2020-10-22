package com.nuc.meerkatx.springbootstudy.controller;

import com.nuc.meerkatx.springbootstudy.pojo.User;
import com.nuc.meerkatx.springbootstudy.queue.KafkaSender;
import com.nuc.meerkatx.springbootstudy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private KafkaSender kafkaSender; //kafka

    @Autowired
    private UserService userService;

    @RequestMapping("/h1")
    public String hello(Model model) {
        log.info("====h1 is running====");
        User user = userService.selectUserById(1);
        log.info(user.toString());
        return "/index.html";
    }

    @RequestMapping("/send/{msg}")
    public String send(@PathVariable("msg") String msg, Model model) {
        kafkaSender.sendChannelMess("demo", msg);//发送到主题demo消息hello,kafka
        return "/index.html";
    }
}
