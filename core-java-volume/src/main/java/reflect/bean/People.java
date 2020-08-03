package reflect.bean;

/**
 * @Auther: 徐少伟
 * @Date: 2019/12/26
 * @Description:
 */
public class People {
    private String name;
    public int age;

    public People() {
    }

    public People(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
