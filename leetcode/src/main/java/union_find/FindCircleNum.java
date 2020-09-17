package union_find;

/**
 * @ClassName: FindCircleNum
 * @Auther: MeerkatX
 * @Date: 2020-09-16 10:31
 * @Description: 547. 朋友圈
 * https://leetcode-cn.com/problems/friend-circles/
 */
public class FindCircleNum {
    public static void main(String[] args) {
        new FindCircleNum().findCircleNum(new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}});
    }

    public int findCircleNum(int[][] M) {
        int[] parent = new int[M.length];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }
        for (int i = 0; i < parent.length; i++) {
            for (int j = 0; j < parent.length; j++) {
                if (M[i][j] == 1) {
                    union(parent, i, j);
                }
            }
        }
        int count = 0;
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == i) count++;
        }
        return count;

    }

    public void union(int[] parent, int a, int b) {
        int ap = find(parent, a);
        int bp = find(parent, b);
        parent[ap] = bp;
    }

    public int find(int[] parent, int n) {
        while (parent[n] != n) {
            parent[n] = parent[parent[n]];//路径压缩
            n = parent[n];
        }
        return n;
    }

}
