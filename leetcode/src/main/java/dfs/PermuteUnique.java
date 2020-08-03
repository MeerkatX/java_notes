package dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/19
 * @Description:
 */
public class PermuteUnique {

    private List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> permuteUnique(int[] nums) {
        int[] marked = new int[nums.length];
        Arrays.sort(nums); //深度优先遍历 回溯法减枝通常需要先排序
        // (这样就可以对比相邻元素是否相等，若这个元素已经标记使用，则跳过)
        dfs(nums,marked,0,new ArrayList<Integer>());
        return ans;
    }

    public void dfs(int[] nums,int[] marked,int deep,ArrayList<Integer> res){
        if(deep == nums.length){
            ans.add(new ArrayList<>(res));
            return;
        }

        for(int i=0;i<nums.length;i++){
            if(marked[i] == 1) continue;

            //判断相邻元素是否相等并且已经生成了答案。减枝
            if(i>0 && nums[i] == nums[i-1] && marked[i-1] == 1) continue;
            //通用回溯模板 设置当前值，进行迭代下一个元素
            marked[i] = 1;
            res.add(nums[i]);
            dfs(nums,marked,deep+1,res);
            //搜索完毕，还原
            marked[i] = 0;
            res.remove(res.size()-1);
        }
    }
}
