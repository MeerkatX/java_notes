package array;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SpiralOrder
 * @Auther: MeerkatX
 * @Date: 2020-09-05 15:29
 * @Description: 哔哩哔哩笔试题 54. 螺旋矩阵
 */
public class SpiralOrder {

    public static void main(String[] args) {
        List<Integer> ans = new SpiralOrder().spiralOrder(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        ans.forEach(System.out::println);
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int total = n * m;
        //前进的方向
        int[] xadd = new int[]{0, 1, 0, -1};
        int[] yadd = new int[]{1, 0, -1, 0};
        List<Integer> ans = new ArrayList<>();
        int x = 0;
        int y = 0;
        int p = 0;//控制前进方向的指针
        //标识在很多算法中都非常重要，不要爱惜空间，标识起来
        boolean[][] seen = new boolean[n][m];//通过seen数组来标识哪些访问过，如果访问过就换方向
        for (int i = 0; i < total; i++) {
            ans.add(matrix[x][y]);//获取当前值，下一个值在之后计算好
            seen[x][y] = true;
            int nextX = x + xadd[p];
            int nextY = y + yadd[p];
            if (0 <= nextX && nextX < n && 0 <= nextY && nextY < m && !seen[nextX][nextY]) {
                x = nextX;//如果再范围内，就不用调整方向
                y = nextY;
            } else {
                p = (p + 1) % 4;//说明到边界了，要换方向 而只有4个方向，如果下一个方向超过4 就取余变为 0
                x += xadd[p];
                y += yadd[p];
            }
        }
        return ans;
    }
}
