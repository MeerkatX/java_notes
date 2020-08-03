package bfs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/31
 * @Description:
 */
public class MinReorder {

    public int minReorder(int n, int[][] connections) {
        int[][] c = new int[n][n];
        int count = 0;
        for (int i = 0; i < connections.length; i++) {
            c[connections[i][0]][connections[i][1]] = 1;
            c[connections[i][1]][connections[i][0]] = -1;
        }
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addLast(0);
        int[] isview = new int[n];
        isview[0] = 1;
        while (!queue.isEmpty()) {
            int root = queue.pollFirst();
            for (int i = 0; i < c[root].length; i++) {
                if (c[root][i] == 1 && isview[i] == 0) {
                    queue.addLast(i);
                    isview[i] = 1;
                    count++;
                } else if (c[root][i] == -1 && isview[i] == 0) {
                    queue.addLast(i);
                    isview[i] = 1;
                }
            }
        }
        return count;

    }

    public int minReorder2(int n, int[][] connections) {
        if (n <= 1 || connections == null || connections.length == 0) {
            return 0;
        }

        Set<Integer> set = new HashSet<>();
        set.add(0);
        int ans = 0;

        for (int i = 0; i < connections.length; i++) {
            if (set.contains(connections[i][1])) {
                set.add(connections[i][0]);
            } else {
                ans++;
                set.add(connections[i][1]);
            }
        }
        return ans;
    }
}
