package dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/23
 * @Description:
 */
public class CombinationSum {


    private List<List<Integer>> ans = new ArrayList<>();

    public static void main(String[] args) {
        new CombinationSum().combinationSum(new int[]{2,3,6,7},7);
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        combinationSumHelper(candidates,target,new ArrayList<Integer>(),0);
        return ans;
    }

    public void combinationSumHelper(int[] candidates, int target,List<Integer> l,int start){
        if(target == 0){
            ans.add(new ArrayList<>(l));
            return;
        }

        for(int i = start;i< candidates.length; i++){
            // start 来保证不会产生重复的结果，因为已经排序，那么
            // 如果得到 2 2 3 接下来 3 再从0开始 3 2 2 会导致结果重复，只能往后
            /*
            在搜索的时候，需要设置搜索起点的下标 begin ，由于一个数可以使用多次，
            下一层的结点从这个搜索起点开始搜索；
            在搜索起点 begin 之前的数因为以前的分支搜索过了，所以一定会产生重复。
             */

            if((target - candidates[i]) < 0)
                break;

            l.add(candidates[i]);
            combinationSumHelper(candidates,target-candidates[i],l,i);
            l.remove(l.size()-1);
        }
    }
}
