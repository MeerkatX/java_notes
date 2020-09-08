package com.nuc.meerkatx.spmvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @ClassName: HelloController
 * @Auther: MeerkatX
 * @Date: 2020-08-05 20:52
 * @Description:
 */
@Controller
@Slf4j
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/h1")
    public String hello() {
        log.info("hello");
        return "hello";
    }

}
