package bs;

/**
 * @ClassName: UpperBound
 * @Auther: MeerkatX
 * @Date: 2020-09-03 16:06
 * @Description: https://www.nowcoder.com/practice/7bc4a1c7c371425d9faa9d1b511fe193?tpId=190&rp=1&ru=%2Factivity%2Foj&qru=%2Fta%2Fjob-code-high-rd%2Fquestion-ranking
 */
public class UpperBound {
    public static void main(String[] args) {
        System.out.println(new UpperBound().upper_bound_(5,4,new int[]{1,2,4,4,5}));
    }
    /**
     * 二分查找
     * @param n int整型 数组长度
     * @param v int整型 查找值
     * @param a int整型一维数组 有序数组
     * @return int整型
     */
    public int upper_bound_ (int n, int v, int[] a) {
        // write code here
        int left = 0;
        int right = n-1;
        while(left <= right){
            int mid = (left + right) >>> 1;
            if(a[mid] > v){
                while(mid > 0 && a[mid-1] == a[mid]) mid--;
                right = mid-1;
            }else if(a[mid] < v){
                while(mid < n-1 && a[mid+1] == a[mid]) mid++;
                left = mid+1;
            }else{
                while(mid > 0 && a[mid-1] == a[mid]) mid--;
                return mid+1;
            }
        }
        return left+1;
    }
}
