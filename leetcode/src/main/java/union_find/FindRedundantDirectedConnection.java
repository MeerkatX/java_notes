package union_find;

/**
 * @ClassName: FindRedundantDirectedConnection
 * @Auther: MeerkatX
 * @Date: 2020-09-17 09:48
 * @Description: 685. 冗余连接 II
 * https://leetcode-cn.com/problems/redundant-connection-ii/
 */
public class FindRedundantDirectedConnection {

    public int[] findRedundantDirectedConnection(int[][] edges) {
        int[] parent = new int[edges.length + 1];
        int[] ancestor = new int[edges.length + 1];
        for (int i = 1; i < parent.length; i++) {
            parent[i] = i;
            ancestor[i] = i;
        }
        int conflict = -1;
        int cycle = -1;
        for (int i = 0; i < edges.length; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            if (parent[b] != b) {
                conflict = i;
            } else {
                parent[b] = a;
                if (find(ancestor, a) == find(ancestor, b)) cycle = i;
                else union(ancestor, a, b);
            }
        }
        if (conflict < 0) {
            return new int[]{edges[cycle][0], edges[cycle][1]};
        } else {
            return cycle >= 0 ? new int[]{parent[edges[conflict][1]], edges[conflict][1]} :
                    new int[]{edges[conflict][0], edges[conflict][1]};
        }
    }

    public void union(int[] parent, int a, int b) {
        int ap = find(parent, a);
        int bp = find(parent, b);
        parent[bp] = ap;
    }

    public int find(int[] parent, int n) {
        if (parent[n] != n) parent[n] = find(parent, parent[n]);//压缩路径
        return parent[n];
    }

}
