

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/4
 * @Description: 区间合并
 */
public class Merge {


    public int[][] merge(int[][] intervals) {
        int len = intervals.length;
        if (len < 2) return intervals;

        // 按照起点排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        List<int[]> res = new ArrayList<>();
        res.add(intervals[0]);

        for (int i = 1; i < len; i++) {
            int[] curInterval = intervals[i];

            // 每次新遍历到的列表与当前结果集中的最后一个区间的末尾端点进行比较
            int[] peek = res.get(res.size() - 1);

            if (curInterval[0] > peek[1]) {
                res.add(curInterval);
            } else {
                // 注意，这里应该取最大
                peek[1] = Math.max(curInterval[1], peek[1]);
            }
        }
        return res.toArray(new int[res.size()][]);


    }
}
