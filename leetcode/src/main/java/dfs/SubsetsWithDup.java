package dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/24
 * @Description:
 */
public class SubsetsWithDup {
    private List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        dfs(nums, 0, 0, new LinkedList<>(), new int[nums.length]);
        ans.add(new ArrayList<>());
        return ans;
    }

    private void dfs(int[] nums, int start, int deep, LinkedList<Integer> l, int[] marked) {
        if (deep == nums.length) return;
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;
            if (marked[i] != 1) {
                marked[i] = 1;
                l.add(nums[i]);
                ans.add(new ArrayList<>(l));
                dfs(nums, i + 1, deep + 1, l, marked);
                marked[i] = 0;
                l.removeLast();
            }
        }

    }
}
