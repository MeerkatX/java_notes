package ioc.pojo;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/23
 * @Description:
 */
@Data
@Component
public class User implements BeanNameAware, BeanFactoryAware, ApplicationContextAware {
    //IOC控制翻转 通过调用set方法来转交控制权到用户手中，将代码耦合度降低即
    /*
        Service s = new ServiceImpl(); //耦合在代码中的

        修改为

        Service s;

        public void setService(Service s){
            this.s = s;
        }
        这样就可以由调用者自己设置要用的实现
     */

    private String name;

    private int age;

    @Autowired
    private Work work;

    private BeanFactory beanFactory;

    ApplicationContext applicationContext;

    public User(){
        System.out.println("init");
    }

    @Override
    public void setBeanName(String s) {
        this.name = s;
        System.out.println("set bean name");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println("set beanFactory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("set application");
    }
}
