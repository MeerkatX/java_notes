package union_find;

/**
 * @ClassName: FindRedundantConnection
 * @Auther: MeerkatX
 * @Date: 2020-09-16 17:18
 * @Description: 684. 冗余连接
 * https://leetcode-cn.com/problems/redundant-connection/
 */
public class FindRedundantConnection {

    public static void main(String[] args) {

    }

    public int[] findRedundantConnection(int[][] edges) {
        int[] parent = new int[edges.length + 1];
        for (int i = 1; i < parent.length; i++) {
            parent[i] = i;
        }
        for (int i = 0; i < parent.length; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            int ap = find(parent, a);
            int bp = find(parent, b);
            if (ap == bp) {
                return new int[]{a, b};
            }
            parent[ap] = bp;
        }
        return new int[2];
    }

    public int find(int[] parent, int a) {
        while (parent[a] != a) {
            parent[a] = parent[parent[a]];//压缩路径
            a = parent[a];
        }
        return a;
    }


}
