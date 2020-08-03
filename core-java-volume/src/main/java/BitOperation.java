import java.util.Scanner;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/20
 * @Description:
 */
public class BitOperation {

    public static void main(String[] args) {
        System.out.println();
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] input = new int[n];
        for (int i = 0; i < n; i++) input[i] = in.nextInt();
        for (int i = 0; i < n; i++) {
            int count = 0;
            while (input[i] != 1) {
                input[i] /= 2;
                count++;
            }
            System.out.println(count + 1);
        }


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
        int d = 1;
        System.out.println(c & d);
        System.out.println(b);
        System.out.println(Integer.MIN_VALUE);
        int f = 5;
        while (f != 1) {
            f >>= 1;
            System.out.println(f);
        }
    }
}
