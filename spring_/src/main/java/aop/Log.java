package aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/28
 * @Description:
 */
@Aspect
public class Log implements MethodBeforeAdvice {
    //目标对象的方法
    //方法参数
    //目标对象
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println(o.getClass().getName() + " " + method.getName());
    }
}
