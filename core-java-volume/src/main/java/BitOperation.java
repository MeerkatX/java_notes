import java.util.Scanner;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/20
 * @Description:
 */
public class BitOperation {

    public static void main(String[] args) {
//        System.out.println();
//        Scanner in = new Scanner(System.in);
//        int n = in.nextInt();
//        int[] input = new int[n];
//        for (int i = 0; i < n; i++) input[i] = in.nextInt();
//        for (int i = 0; i < n; i++) {
//            int count = 0;
//            while (input[i] != 1) {
//                input[i] /= 2;
//                count++;
//            }
//            System.out.println(count + 1);
//        }


        // 负数为补码形式
        //  1 的原码 00000001
        //  1 的反码 11111110
        //  1 的补码 11111110 + 1 -> 11111111 即 -1

        int a = 1;
        int b = ~a;

        int c = -1;
        // 负数右移补1 ，左移补0
        System.out.println(c << 1);
        System.out.println(c >> 1);
        System.out.println(c >>> 1);//无符号 右移补0
        System.out.println("=======================================");
        int d = 1;
        System.out.println(c & d);
        System.out.println(b);
        System.out.println(Integer.MIN_VALUE);
        int f = 5;
        while (f != 1) {
            f >>= 1;
            System.out.println(f);
        }

        System.out.println("---------------------------------");

        int demo = 1;
        demo <<= -1;//取后5位，11110 -1的补码 11111 等于31 所以等于左移31位
        int demo2 = 1;
        demo2 <<= 31;
        int demo3 = 1;
        demo3 <<= 1;
        System.out.println(demo + "             " + demo2 + "              " + demo3);

    }
}
