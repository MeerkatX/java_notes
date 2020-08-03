package reflect;

import annotation.MyAnnotation;
import reflect.controller.UserController;

import java.util.stream.Stream;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/3
 * @Description: 反射根据注解自动注入的例子
 */
public class Main2 {

    public static void main(String[] args) {
        UserController userController = new UserController();
        Class clazz = userController.getClass();
        Stream.of(clazz.getDeclaredFields()).forEach(field -> {

            //获取注解 RUNTIME 才行 @Retention(RetentionPolicy.RUNTIME)
            MyAnnotation annotation = field.getAnnotation(MyAnnotation.class);
            if (annotation != null) {
                //如果有注解 如AutoWried则设置值
                field.setAccessible(true);
                Class type = field.getType();
                try {

                    Object o = type.newInstance(); //spring在newInstance又更精细的操作（循环依赖）
                    field.set(userController, o);//将值set为 o

                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        });

        System.out.println(userController.getUserService());
    }
}
