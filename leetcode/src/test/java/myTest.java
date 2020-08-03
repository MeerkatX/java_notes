import org.junit.Test;
/**
 * @Auther: 徐少伟
 * @Date: 2020/5/27
 * @Description:
 */
public class myTest {

    @Test
    public void test(){
        ThreeSum threeSum = new ThreeSum();
        threeSum.threeSum(new int[]{-1, 0, 1, 2, -1, -4}).stream().forEach(o -> {
            System.out.println(o + " ");
        });
        System.out.println("hello junit");
    }
}
