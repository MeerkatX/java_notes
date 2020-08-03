

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/6
 * @Description:
 */
public class MaxProduct {

    public int maxProduct(int[] nums) {
        if(nums.length==0)
            return 0;
        int max = -Integer.MAX_VALUE; //用来存总体最大值。
        int imax = 1; //存当前值的最大值
        int imin = 1; //因为可能有负数，负数乘最小值会得到最大值，乘最大值会变成最小值，所以需要记录最小值
        for(int i=0;i<nums.length;i++){
            if(nums[i]<0){
                //当当前值是负数时，就交换两者
                int t = imax;
                imax = imin;
                imin = t;
            }
            imax = Math.max(imax*nums[i],nums[i]);
            imin = Math.min(imin*nums[i],nums[i]);
            max = Math.max(max,imax);
        }
        return max;
    }

}
