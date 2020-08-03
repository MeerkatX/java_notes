package reflect;

import reflect.controller.UserController;
import reflect.service.UserService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/3
 * @Description: 反射自动注入的例子
 */
public class Main {

    public static void main(String[] args) throws Exception {
        UserController userController = new UserController();
        Class clazz = userController.getClass();
        UserService service = new UserService();
        Field serviceField = clazz.getDeclaredField("userService");
        serviceField.setAccessible(true);//设置为可访问 那么就可以访问private的field

        String name = serviceField.getName();
        name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
        String setName = "set" + name;

        Method method = clazz.getMethod(setName, UserService.class);
        method.invoke(userController, service);

        System.out.println(userController.getUserService());

    }
}
