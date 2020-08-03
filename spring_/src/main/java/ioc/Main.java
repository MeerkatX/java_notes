package ioc;

import ioc.pojo.User;
import ioc.pojo.Work;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/23
 * @Description:
 */
@Slf4j
public class Main {
    public static void main(String[] args) {

//        DispatcherServlet
//        ApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
        ApplicationContext app = new AnnotationConfigApplicationContext(Config.class);
        User user = (User) app.getBean("user"); //循环依赖
        Work work = app.getBean("work",Work.class);
        log.info(work.user.toString());
        System.out.println(work.user.toString());
        System.out.println(user.toString());
    }
}
