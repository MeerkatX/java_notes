import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/19
 * @Description:
 */
public class MainTest {


    public static void main(String[] args) {

        int j = 1;
        if ((j = 2) == 3) {
            System.out.println(j);
        } else if ((j = 3) == 2) {
            System.out.println(j);
        } else {
            System.out.println(j);
        }


        int i = 1;
        i = i++ + i++;
        System.out.println(i); //
        i = 1;
        i = ++i + i++;
        System.out.println(i);
        i = 1;
        i = i++ + ++i;
        System.out.println(i);
        i = 1;
        i += i++; //这个比较容易记错
        System.out.println(i);
        i = 1;
        i += ++i;
        System.out.println(i);
        i = 1;
        i = i++;
        System.out.println(i);

        Deque<Character> deque = new ArrayDeque();
        deque.poll();
    }
}
