package reflect;

import reflect.bean.People;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Auther: 徐少伟
 * @Date: 2019/12/26
 * @Description: java核心编程思想练习代码
 */
public class ReflectDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //-----------------反射------------------------
        People people = new People("pp", 13);
        Class p = people.getClass(); //Class p = People.class;
        Method[] methods = p.getDeclaredMethods(); //所有共有方法包含父类
        Field[] fields = p.getFields(); //所有共有域
        //声明获取的该类的域
        Field field = p.getDeclaredField("age");
        Field field_private = p.getDeclaredField("name");//name为private
        field_private.setAccessible(true);//设置为true可以访问私有方法，不然会报错
        //获取people对象的域
        field.set(people, 1);
        System.out.println(field.get(people));
        System.out.println(field_private.get(people));
        Arrays.stream(methods).forEach(method -> {
            System.out.println(method.getName());
        });
        //---------------invoke-------------------------
        Method method = p.getMethod("setName", String.class);//可以用在重载上不同参数
        method.invoke(people, "xsw");//后面是该方法的参数 setName("") 类似这样
        System.out.println(people.getName());
        //----------------

    }
}
