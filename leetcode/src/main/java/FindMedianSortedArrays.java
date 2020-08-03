package leetcode;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/15
 * @Description:
 */
public class FindMedianSortedArrays {
    public static void main(String[] args) {
        FindMedianSortedArrays findMedianSortedArrays = new FindMedianSortedArrays();
        int[] nums1 = {1, 2, 3};
        int[] nums2 = {2, 5, 8};
        System.out.println(findMedianSortedArrays.findMedianSortedArrays(nums1, nums2));
    }

    //归并 O(M+N)
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length = nums1.length + nums2.length;
        int[] nums = new int[length];//辅助数组
        boolean flag = length % 2 == 0;
        int mid = length / 2;
        int i = 0;
        int j = 0;
        for (int k = 0; k < length; k++) {
            // m + n 挨个比较，直到 i j 超过了自身长度
            if (i >= nums1.length)
                nums[k] = nums2[j++];
            else if (j >= nums2.length)
                nums[k] = nums1[i++];
            else if (nums1[i] > nums2[j])
                nums[k] = nums2[j++];
            else
                nums[k] = nums1[i++];
        }
        return flag ? ((double) (nums[mid] + nums[mid - 1]) / 2) : nums[mid];

    }

    //二分查找 O(log(M+N))
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;

        int left = (n + m + 1) / 2; //求第K位，如果n=5 m=5 中位为 (5 加 6 除 2 ) 其中 6 就是right算出来的
        int right = (n + m + 2) / 2;//如果加起来是奇数 如n=5 m=6 中位相同为 6

        //将偶数和奇数的情况合并，如果是奇数，会求两次同样的 k 。
        return (getKth(nums1, 0, n - 1, nums2, 0, m - 1, left)
                + getKth(nums1, 0, n - 1, nums2, 0, m - 1, right)) * 0.5;
    }

    //通过递归变化 查找第 k 位，每次查找两个数组 k/2 的值 就能简单的将范围缩小一半 所以时间复杂度可以是log2(M+N)
    //下次再查找是 k - k/2 位
    private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        //让 len1 的长度小于 len2，这样就能保证如果有数组空了，一定是 len1
        //这里仅仅是交换了两者位置
        if (len1 > len2) return getKth(nums2, start2, end2, nums1, start1, end1, k);
        //如果 数组1 没有数字，那么肯定在数组2 中
        if (len1 == 0) return nums2[start2 + k - 1];
        //当递归到 k=1 时说明只要判断哪个首位数小就是结果
        if (k == 1) return Math.min(nums1[start1], nums2[start2]);

        // min (len1,k/2) 是为了防止 k/2 超过了数组长度，如果超过长度了，就说明到末位了
        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        if (nums1[i] > nums2[j]) {
            //如果数组1大于数组2 说明数组2的前 k/2 位（计算得到的 j ） 都不是第k个元素
            //所以继续从数组2的j+1为开始，搜索 k - k/2 （或数组2长度）位，因为已经排除了
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            //同理
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }

}
