

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/21
 * @Description:
 */
public class SearchInsert {

    public static void main(String[] args) {
        new SearchInsert().searchInsert(new int[]{1,3,5,6},5);
    }

    public int searchInsert(int[] nums, int target) {
        return bSearch(nums,0,nums.length-1,target);
    }

    public int bSearch(int[] nums,int left,int right,int target){
        if(left > right) return left;
        int mid = left + (right-left)/2;
        if(nums[mid] == target) return mid;
        if(nums[mid]<target) return bSearch(nums,mid+1,right,target);
        else return bSearch(nums,left,mid-1,target);
    }
}
