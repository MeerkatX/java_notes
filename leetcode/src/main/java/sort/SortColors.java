package sort;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/25
 * @Description:
 */
public class SortColors {

    public static void main(String[] args) {
        new SortColors().sortColors(new int[]{2,0,1});
    }

    public void sortColors(int[] nums) {
        int partition = 1;
        int i=-1,j=nums.length-1;
        while(true){
            while(nums[++i] < partition)if (i==nums.length) break;
            while(nums[j]> partition)j--;
            if(i>=j) break;
            int swap = nums[i];
            nums[i] = nums[j];
            nums[j] = swap;
        }
    }
}
