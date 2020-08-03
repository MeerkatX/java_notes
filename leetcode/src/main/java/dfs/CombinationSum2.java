package dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/7
 * @Description: 40. 组合总和 II
 */
public class CombinationSum2 {

    private List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        dfs(candidates,target,0,new ArrayList<Integer>(),0);
        return ans;
    }

    public void dfs(int[] candidates, int target, int sum,List<Integer> one,int start){
        if(sum == target){
            ans.add(new ArrayList(one));
            return;
        }
        if(sum > target){
            return;
        }
        for(int i = start; i< candidates.length; i++){

            if(candidates[i]> target) continue;
            if(i>start && candidates[i]==candidates[i-1]) continue;

            one.add(candidates[i]);
            dfs(candidates,target,sum+candidates[i],one,i+1);
            one.remove(one.size()-1);
        }

    }

}
