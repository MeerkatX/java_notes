package greedy;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * @ClassName: CanJump2
 * @Auther: MeerkatX
 * @Date: 2020-09-15 17:23
 * @Description:
 */
public class CanJump2 {

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        BigInteger zero = new BigInteger("1");
        BigInteger one = new BigInteger("1");
        BigInteger two = new BigInteger("1");
        for(int i= 3; i<=n ;i++){
            BigInteger temp = two.add(zero);
            zero = one;
            one = two;
            two = temp;
        }
        System.out.println(two.toString());
    }
}
