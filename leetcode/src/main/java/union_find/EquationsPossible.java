package union_find;

import java.util.*;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/8
 * @Description:
 */
public class EquationsPossible {
    public static void main(String[] args) {
        new EquationsPossible().equationsPossible(new String[]{
                "a==b","b==c","c!=d","e==e","f==d","d==a"
        });
    }
    public boolean equationsPossible(String[] equations) {
        int[] parent = new int[26];
        for(int i=0;i<parent.length;i++){
            parent[i] = i;
        }

        for(String e:equations){
            if(e.charAt(1)=='='){
                int a = e.charAt(0)-'a';
                int b = e.charAt(3)-'a';
                union(parent,a,b);
            }
        }

        for(String e:equations){
            if(e.charAt(1)=='!'){
                int a = e.charAt(0)-'a';
                int b = e.charAt(3)-'a';
                int ap = find(parent,a);
                int bp = find(parent,b);
                if(ap == bp) return false;
            }
        }
        return true;
    }

    public void union(int[] parent,int a,int b){
        int ap = find(parent,a);
        int bp = find(parent,b);
        parent[bp] = ap;//找到a,b的根节点，将两者合并即简单的将 bp 指向 ap
    }

    public int find(int[] parent,int a){
        while(parent[a]!= a){
            //如果父类就是自身说明已经是根了
            parent[a] = parent[parent[a]];// parent[a] 等于 父类的父类 parent[parent[a]]
            a = parent[a];//a再等于父类
        }
        return a;
    }
}
