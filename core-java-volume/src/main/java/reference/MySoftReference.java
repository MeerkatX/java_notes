package reference;

import java.lang.ref.SoftReference;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/2
 * @Description:
 */
public class MySoftReference {

    public static void main(String[] args) {
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024 * 1024 * 10]);

        System.out.println(m.get());
        System.gc();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(m.get());
        // -Xmx25M
        /*
            [B@1b6d3586
            [B@1b6d3586
            null
         */
        byte[] b = new byte[1024 * 1024 * 15];
        System.out.println(m.get());

    }

}
