import org.openjdk.jol.info.ClassLayout;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/31
 * @Description:
 */
public class MarkWordTest {


    public static void main(String[] args) {
        
        Object o = new Object();
        //对象头 markword
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o) {
            //锁升级 无锁->偏向锁->轻量级锁->重量级锁 会修改markword并且将原markword保存于栈帧
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
            System.out.println("hello");
        }
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

    }
}
