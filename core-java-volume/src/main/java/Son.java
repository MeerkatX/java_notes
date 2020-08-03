
import java.util.PriorityQueue;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/19
 * @Description:
 */
class Son extends Father implements TalkName {
    //class Son extends Father implements TalkName{

    public static void main(String[] args) {
        // 默认实现 如果超类，接口中默认实现方法名相同，使用超类方法(覆盖了接口的)
        // 但是如果是这种情况必须将 超类方法的 具体方法改为 public 因为可以通过
        // 接口访问该方法，而接口中所有方法访问权限为 public 会通不过编译

    }
}
