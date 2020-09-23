package reference;


import java.lang.ref.SoftReference;

/**
 * @ClassName: SoftTest
 * @Auther: MeerkatX
 * @Date: 2020-09-18 20:39
 * @Description:
 */
public class SoftTest {

    private static class Bigger {
        public int[] value;
        public String name;

        public Bigger(String name) {
            this.name = name;
            value = new int[1024];
        }

    }

    public static void main(String[] args) {
        int count = 100000;
        SoftReference[] values = new SoftReference[count];
        for (int i = 0; i < count; i++) {
            values[i] = new SoftReference<Bigger>(new Bigger("b-" + i));
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(((Bigger) (values[i].get())).name);
        }
    }
}
