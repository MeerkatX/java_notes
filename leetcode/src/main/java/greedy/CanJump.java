package greedy;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/4
 * @Description: 55. 跳跃游戏
 * https://leetcode-cn.com/problems/jump-game/
 */
public class CanJump {

    public boolean canJump(int[] nums) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > k) return false;
            k = Math.max(k, nums[i] + i);
        }
        return true;
    }

}
