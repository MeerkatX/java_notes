
/**
 * @Auther: 徐少伟
 * @Date: 2020/4/21
 * @Description:
 */
public interface InterFaceTest {
    public final static int a = 1;

    public default void abc() {
        System.out.println("sss");
    }
}
