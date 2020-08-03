package tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/19
 * @Description:
 */
public class RedBlackTree {

    private class Node{
        private int value;
        Node left,right;
        int N;
        boolean color;
    }

    public static void main(String[] args) {



        String[] tes = "Abbbbbbb cd eee aaaaa".toLowerCase().split(" ");
        Arrays.sort(tes,(one,two)->{
            return one.length()-two.length();
        });
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<tes.length-1;i++) {
            if(i==0) tes[0] = Character.toUpperCase(tes[i].charAt(0)-32) + tes[0].substring(1);
            sb.append(tes[i]+" ");
        }
        sb.append(tes[tes.length-1]);
        System.out.println(sb.toString());
    }

}
