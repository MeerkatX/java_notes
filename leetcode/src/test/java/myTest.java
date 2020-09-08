import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/27
 * @Description:
 */
public class myTest {

    @Test
    public void test(){

        String a = new String("abc");
        String b = new String("abc");
        HashSet<String> set = new HashSet<>();
        set.add(a);
        set.add(b);
        System.out.println(set.size());
        System.out.println(a==b);
        System.out.println(a.hashCode()+" "+b.hashCode());

        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
    }
 }
