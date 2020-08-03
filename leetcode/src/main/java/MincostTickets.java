

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/6
 * @Description: 最低票价
 */
public class MincostTickets {

    public int mincostTickets(int[] days, int[] costs) {
        if (days.length == 0)
            return 0;
        int[] dp = new int[days[days.length - 1] + 1]; // 备忘录 也可以说是dp数组 记录每天所需最小花费
        for (int day : days) dp[day] = Integer.MAX_VALUE; // 标记是否应该买票
        for (int i = 1; i < dp.length; i++) {
            //循环从 1 到 days 的最后一天间 的 每一天 ，计算他们的最小花费
            if (dp[i] == 0) {
                //如果 今天 即[i]等于0说明不需要买票，那么钱数就和昨天是一样的
                dp[i] = dp[i - 1];
                continue;
            }
            // 如果需要买票
            int n1 = dp[i - 1] + costs[0]; // 一日票 昨天最小花费加 一日票价格
            int n2 = i > 7 ? dp[i - 7] + costs[1] : costs[1]; // 七日 七天前最小票价 加 7日票
            int n3 = i > 30 ? dp[i - 30] + costs[2] : costs[2];
            dp[i] = Math.min(Math.min(n1, n2), n3); // 求最小值

            // 这里思想：从后往前想 如果要获得今天的最小票价，那么只要前 1 天 加 一日票
            // 或者 7 天， 30 天的前 加 各自票价里的最小，那么肯定就是最小了(因为前...天都是最小值)。
        }
        return dp[days[days.length - 1]];
    }
}
