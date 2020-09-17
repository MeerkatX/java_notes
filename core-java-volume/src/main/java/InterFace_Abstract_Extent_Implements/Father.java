package InterFace_Abstract_Extent_Implements;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/19
 * @Description:
 */
public abstract class Father {

    private String name = "Father";

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

//    public Father(String n){
//        name = n;//如果子类没有调用该方法的话，无法编译
//    }

//    String getName(){
//        return name;
//    }
}
