package InterFace_Abstract_Extent_Implements;

import jdk.nashorn.internal.objects.annotations.Function;

/**
 * @ClassName: FunInterFace
 * @Auther: MeerkatX
 * @Date: 2020-08-10 10:37
 * @Description: 加不加@FunctionalInterface对于接口是不是函数式接口没有影响，
 * 该注解知识提醒编译器去检查该接口是否仅包含一个抽象方法
 */
@FunctionalInterface
public interface FunInterFace {

    void speak();

    default void helloDefault() {
        System.out.println("helloDefault");
    }

    static void helloStatic() {
        System.out.println("helloStatic");
    }

    //会报错，因为函数式接口只能有一个抽象方法
    //void hello();
}
