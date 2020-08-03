package leetcode;

import java.util.Scanner;

/**
 * @Auther: 徐少伟
 * @Date: 2020/2/11
 * @Description:
 */
public class cut {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);//以空格作为分隔符
        int x = scan.nextInt();
        int y = scan.nextInt();
        int z = scan.nextInt();
        int k = scan.nextInt();

        int cut_x = 0;
        int cut_y = 0;
        int cut_z = 0;


        scan.close();//关闭扫描器
        while (k > 0) {
            if (cut_x < x) {
                ++cut_x;
                --k;
            }
            if (cut_y < y) {
                ++cut_y;
                --k;
            }
            if (cut_z < z) {
                ++cut_z;
                --k;
            }

        }

        System.out.println(
                (cut_x + 1) * (cut_y + 1) * (cut_z + 1)
        );
    }
}
