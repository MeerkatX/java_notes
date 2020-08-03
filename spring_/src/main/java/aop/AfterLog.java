package aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/28
 * @Description:
 */
@Aspect   // aspect注解为使用aspectJ
@Component
public class AfterLog implements AfterReturningAdvice {

    //使用spring aop 和 cglib 需要 实现 或 继承 相应方法

//    @Pointcut("execution(* aop.UserServiceImpl.*(..))")
//    public void pointCut() {
//    }


    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        // o 即result
        System.out.println(o1.getClass().getName() + " " + method.getName() + " " + o);
    }

//    @After("pointCut()")
//    public void after() throws Throwable {
//        // o 即result
//        System.out.println("结束");
//    }
//
//    @Before("pointCut()")
//    public void before() throws Throwable {
//        // o 即result
//        System.out.println("开始");
//    }
}
