package InterFace_Abstract_Extent_Implements;

import lombok.Data;

/**
 * @ClassName: FatherNotAbs
 * @Auther: MeerkatX
 * @Date: 2020-08-10 11:04
 * @Description:
 */
//final类不可继承
public class FatherNotAbs {
    public String name;
    private int age;
    protected String desc;
    String other;

    public static int stat;

    static {
        stat = 1;
        System.out.println("father static init block");
    }

    {
        name = "FatherNotAbs";
        age = 1;
        System.out.println("father init block");
    }

    public FatherNotAbs(String desc) {
        this.desc = desc;
        other = "other";
        System.out.println("father init method with desc");
    }

    public FatherNotAbs() {
        this.desc = "desc";
        other = "other";
        System.out.println("father init method no args");
    }

    public void speakAge() {
        System.out.println("father method age is " + this.age);
    }

    //final方法不可重写
    final public void speakName() {
        System.out.println("father method name is " + this.name);
    }

    protected void speakDesc() {
        System.out.println("father method desc is " + this.desc);
    }
}
