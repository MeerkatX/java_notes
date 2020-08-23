package InterFace_Abstract_Extent_Implements;

import InterFace_Abstract_Extent_Implements.FatherNotAbs;
import InterFace_Abstract_Extent_Implements.FunInterFace;
import InterFace_Abstract_Extent_Implements.TalkName;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/19
 * @Description:
 */
class Son extends FatherNotAbs implements TalkName {

    public String name;//注意，变量是没有多态的

    public Son(String desc) {
        this.desc = desc;//覆盖父类参数，可以继承protected修饰的变量
        //this.age = 1; 报错，子类不继承父类private
        this.other = "father to son other";//将Son移到当前包外时，不可见
    }

    public Son() {
        this.name = "son name";
    }

    @Override
    public void speakAge() {
        super.speakAge();
    }

    //同理不继承，不可重写父类private方法

    //final方法不可重写

    @Override
    public void speakDesc() {
        //private void speakDesc() { 重写父类方法访问范围只能比父类大，不能比父类小
        System.out.println("son speakDesc " + this.desc);
    }

    @Override
    public String getName() {
        return this.name;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    //函数式接口使用
    public void speak(FunInterFace funInterFace) {
        funInterFace.speak();
    }
}
