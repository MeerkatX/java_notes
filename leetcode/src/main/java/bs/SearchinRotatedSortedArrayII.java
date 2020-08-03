package bs;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/1
 * @Description:
 */
public class SearchinRotatedSortedArrayII {
    public boolean search(int[] nums, int target) {
        //去重复
        if (nums.length == 0) return false;

        int p = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[p]) nums[++p] = nums[i];
        }
        //二分查找
        int start = 0;
        int end = p;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) return true;

            if (nums[start] <= nums[mid]) {
                if (nums[start] <= target && nums[mid] > target) end = mid - 1;
                else start = mid + 1;
            } else {
                if (nums[mid] < target && nums[end] >= target) start = mid + 1;
                else end = mid - 1;
            }
        }
        return false;
    }

    public boolean search2(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int start = 0;
        int end = nums.length - 1;
        int mid;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return true;
            }
            ////////////////////////////////////////重点/////////////////////////////////////
            if (nums[start] == nums[mid]) {
                start++;
                continue;
            }
            // 10111 和 1110111101 这种。此种情况下 nums[start] == nums[mid]，
            // 分不清到底是前面有序还是后面有序，此时 start++ 即可。相当于去掉一个重复的干扰项。
            ///////////////////////////////////////////////////////////////////////////////////
            //前半部分有序
            if (nums[start] < nums[mid]) {
                //target在前半部分
                if (nums[mid] > target && nums[start] <= target) {
                    end = mid - 1;
                } else {  //否则，去后半部分找
                    start = mid + 1;
                }
            } else {
                //后半部分有序
                //target在后半部分
                if (nums[mid] < target && nums[end] >= target) {
                    start = mid + 1;
                } else {  //否则，去后半部分找
                    end = mid - 1;

                }
            }
        }
        //一直没找到，返回false
        return false;

    }
    /*
    作者：reedfan
    链接：https://leetcode-cn.com/problems/search-in-rotated-sorted-array-ii/solution/zai-javazhong-ji-bai-liao-100de-yong-hu-by-reedfan/
    来源：力扣（LeetCode）
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    */


}
