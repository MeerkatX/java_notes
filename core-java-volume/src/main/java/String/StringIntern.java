package String;

/**
 * @ClassName: StringIntern
 * @Auther: MeerkatX
 * @Date: 2020-08-30 16:55
 * @Description: 有关常量池、堆的知识点
 */
public class StringIntern {
    public static void main(String[] args) {

        String s1 = new String("a") + new String("a");
        System.out.println(System.identityHashCode(s1));

        //String s2 = "aa";
        //s1 = s1.intern();//返回的是常量池中引用值，
        s1.intern();
        String s3 = new String("aa");
        s3.intern();
        String s2 = "aa";//移动s2位置会导致结果不同


        System.out.println(System.identityHashCode(s1));
        System.out.println(System.identityHashCode(s2));
        System.out.println(System.identityHashCode(s3));
        System.out.println((s1 == s2) + "|" + (s2 == s3) + "|" + (s1 == s3));
    }
}
