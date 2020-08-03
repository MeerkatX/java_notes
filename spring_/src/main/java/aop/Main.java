package aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/23
 * @Description:
 */

public class Main {
    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("aop.xml");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        //动态代理的是接口
        UserService userService = applicationContext.getBean("userServiceImpl", UserService.class);
        userService.add();
    }

}
