package dp;

/**
 * @ClassName: MaxsumofSubarray
 * @Auther: MeerkatX
 * @Date: 2020-09-14 15:49
 * @Description: 子数组的最大累加和问题
 * 给定一个数组arr，返回子数组的最大累加和
 * 例如，arr = [1, -2, 3, 5, -2, 6, -1]，所有子数组中，[3, 5, -2, 6]可以累加出最大的和12，所以返回12.
 * [要求]
 * 时间复杂度为O(n)，空间复杂度为O(1)
 * https://www.nowcoder.com/practice/554aa508dd5d4fefbf0f86e5fe953abd?tpId=188&&tqId=35291&rp=1&ru=/activity/oj&qru=/ta/job-code-high-week/question-ranking
 */
public class MaxsumofSubarray {
    /**
     * max sum of the subarray
     * @param arr int整型一维数组 the array
     * @return int整型
     */
    public int maxsumofSubarray (int[] arr) {
        // write code here
        int res = 0;
        int dp = 0;
        for(int i=0; i< arr.length; i++){
            dp+=arr[i];
            if(dp>=0) res=Math.max(res,dp);
            else{
                dp=0;
            }
        }
        return res;
    }
}
