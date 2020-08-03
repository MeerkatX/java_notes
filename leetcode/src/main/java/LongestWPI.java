

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/20
 * @Description: 表现良好的最长时间段
 *
 * 前缀和
 */
public class LongestWPI {

    public static void main(String[] args) {
        new LongestWPI().longestWPI(new int[]{9,6,2,7,9,3,8,9,6,9,9});
    }



    public int longestWPI(int[] hours) {
        int sum = 0;
        int res = 0;
        Map<Integer, Integer> sumToIndex = new HashMap<>();
        for(int i = 0; i < hours.length; i++){
            int temp = hours[i] > 8 ? 1 : -1;
            sum += temp;
            if(sum > 0)
                res = i + 1;
            else {
                if(!sumToIndex.containsKey(sum))
                    sumToIndex.put(sum, i);
                if(sumToIndex.containsKey(sum - 1)) //当 k = 1 时
                    res = Math.max(res, i - sumToIndex.get(sum - 1));
            }
        }
        return res;
    }

}
