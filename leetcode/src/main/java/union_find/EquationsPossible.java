package union_find;

import java.util.*;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/8
 * @Description: 990. 等式方程的可满足性
 * https://leetcode-cn.com/problems/satisfiability-of-equality-equations/
 */
public class EquationsPossible {
    public static void main(String[] args) {
        new EquationsPossible().equationsPossible(new String[]{
                "a==b","b==c","c!=d","e==e","f==d","d==a"
        });
    }
    public boolean equationsPossible(String[] equations) {
        int[] parent = new int[26];//通过数组记录树
        for(int i=0;i<parent.length;i++){
            parent[i] = i;
        }

        for(String e:equations){
            //先合并相等的集合，构成一个连通图
            if(e.charAt(1)=='='){
                int a = e.charAt(0)-'a';
                int b = e.charAt(3)-'a';
                union(parent,a,b);
            }
        }

        for(String e:equations){
            if(e.charAt(1)=='!'){
                //再遍历不等，调查是否在同一个连通图里，如果在的话就说明不对。
                int a = e.charAt(0)-'a';
                int b = e.charAt(3)-'a';
                int ap = find(parent,a);
                int bp = find(parent,b);
                if(ap == bp) return false;
            }
        }
        return true;
    }

    //合并，其实就是将包含b的树的根指向a
    public void union(int[] parent,int a,int b){
        int ap = find(parent,a);
        int bp = find(parent,b);
        parent[bp] = ap;//找到a,b的根节点，将两者合并即简单的将 bp 指向 ap
    }

    //向上递归查找父类
    public int find(int[] parent,int a){
        //初始化时 parent[i] = i;
        while(parent[a]!= a){
            //如果父类就是自身说明已经是根了
            parent[a] = parent[parent[a]];// parent[a] 等于 父类的父类 parent[parent[a]] 路径压缩
            a = parent[a];//a再等于父类
        }
        return a;
    }
}
