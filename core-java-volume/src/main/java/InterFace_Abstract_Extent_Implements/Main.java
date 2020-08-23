package InterFace_Abstract_Extent_Implements;

/**
 * @ClassName: Main
 * @Auther: MeerkatX
 * @Date: 2020-08-10 10:43
 * @Description:
 */
public class Main {

    public static void main(String[] args) {
        // 默认实现 如果超类，接口中默认实现方法名相同，使用超类方法(覆盖了接口的)
        // 但是如果是这种情况必须将 超类方法的 具体方法改为 public 因为可以通过
        // 接口访问该方法，而接口中所有方法访问权限为 public 会通不过编译

        FatherNotAbs son0 = new Son("son desc");
        son0.speakDesc();
        son0.speakName();// 调用父类方法，就用的是父类的name 即变量没有多态 （子类重新定义了public String name）
        son0.speakAge();
        //如果要调用子类方法只能转换类型为Son
        System.out.println(((Son) son0).getName());
        System.out.println("============================ son1 ========================================");

        Son son1 = new Son();
        son1.speakName();// 调用父类方法，就用的是父类的name 即变量没有多态 （子类重新定义了public String name）
        son1.speakAge();
        System.out.println(son1.getName());



        System.out.println("========================= FuncInterFace ====================================");
        //////////////////////////////////函数式接口///////////////////////////////////////
        Son son2 = new Son();
        son2.speak(() -> System.out.println("funInterFace"));
    }
}
