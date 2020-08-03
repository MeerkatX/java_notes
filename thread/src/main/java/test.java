

import java.util.*;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/21
 * @Description:
 * 拼多多 2020 第3题
 * 需要减枝 不然超时
 */
public class test {
    private static int N;
    private static int M;
    private static int length;

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        N = in.nextInt();
        M = in.nextInt();
        length = in.nextInt();
        dfs(new StringBuilder(),0,0,0);
    }

    private static void dfs(StringBuilder sb,int deep,int n,int m){
        if(deep == length) {System.out.println(sb.toString()); return;}
        for(int i = 0; i<2;i++){
            if(i == 0 && n<N ){
                sb.append('a');
                System.out.println(sb.toString());
                dfs(sb,deep+1,n+1,m);
                sb.deleteCharAt(sb.length()-1);
            }
            if(i==1 && m<M){
                sb.append('b');
                System.out.println(sb.toString());
                dfs(sb,deep+1,n,m+1);
                sb.deleteCharAt(sb.length()-1);
            }
        }

    }
}
