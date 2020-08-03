package dfs;

import java.util.HashSet;
import java.util.Stack;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/18
 * @Description: 课程表II
 *
 * 图的深度，广度优先遍历
 */
public class FindOrder {

    /**
     * 广度优先遍历版 （拓扑排序）
     */










    /**
     * 深度优先遍历版 （拓扑排序）
     */
    private Stack<Integer> ansStack = new Stack<>();

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        //生成 有向图 (有两种方式 1 邻接矩阵 2 保存通向的节点) 当前为方法2
        HashSet<Integer>[] graph = new HashSet[numCourses];
        for(int i= 0;i<numCourses;i++) graph[i] = new HashSet<>();
        for(int[] path: prerequisites){
            graph[path[1]].add(path[0]);
        }

        //生成标记数组，用来表示该节点是否被访问过 必备
        int[] marked = new int[numCourses];
        //从头开始遍历每个节点
        for(int i=0; i<numCourses; i++){
            //如果有环就直接返回了
            if(!dfs(graph,i,marked)) return new int[0];
        }
        //格式化输出
        int[] ans  = new int[numCourses];
        int idx = 0;
        while(!ansStack.isEmpty()){
            ans[idx++] = ansStack.pop();
        }
        return ans;
    }


    private boolean dfs(HashSet<Integer>[] graph,int v,int[] marked){
        if(marked[v] == 1) return false;//判断是否有环 即当前是否访问过该节点现在又访问了一遍
        if(marked[v] == -1) return true;
        marked[v]=1; //标记 正在访问(因为可能会存在环的可能性) 如果存在环的话将变成死循环
        for(int next: graph[v]){
            if(!dfs(graph,next,marked)) return false;
        }
        marked[v]=-1;//当确定无环，无其他路径通往其他节点时(即最深处) 标记为 -1
        ansStack.push(v);//压入栈
        return true;
    }

}
