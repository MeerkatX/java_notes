
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/12
 * @Description:
 */
public class Permute {
    public static void main(String[] args) {
        List<List<Integer>> ans = new Permute().permute(new int[]{1, 2, 3});
        ans.stream().forEach(System.out::println);

    }

    private List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        permuteHelper(0, nums, new ArrayList<>());
        return ans;
    }

    private void permuteHelper(int y, int[] nums, List<Integer> l) {
        if (y == nums.length) {
            ans.add(new ArrayList<>(l));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != Integer.MAX_VALUE) {
                l.add(nums[i]);
                nums[i] = Integer.MAX_VALUE;
            }else continue;
            permuteHelper(y + 1, nums, l);
            if(l.size()>0) nums[i] = l.remove(l.size()-1);
        }
    }
}
