package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/30
 * @Description:
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    /*

    注解原理是什么
    注解本质是一个继承了Annotation的特殊接口，其具体实现类是Java运行时生成的动态代理类。
    我们通过反射获取注解时，返回的是Java运行时生成的动态代理对象。
    通过代理对象调用自定义注解的方法，会最终调用AnnotationInvocationHandler的invoke方法。
    该方法会从memberValues这个Map中索引出对应的值。
    而memberValues的来源是Java常量池。

    保留到不同时间段
        RetentionPolicy.RUNTIME 在运行时加载
        RetentionPolicy.CLASS 只在类文件中，不在运行时加载
        RetentionPolicy.SOURCE 只在源码
     */
}
