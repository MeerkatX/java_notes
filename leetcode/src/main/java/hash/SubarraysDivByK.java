package hash;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/27
 * @Description:
 */
public class SubarraysDivByK {
    public static void main(String[] args) {
        System.out.println(new SubarraysDivByK().subarraysDivByK(new int[]{-1,2,9},2));
    }


    public int subarraysDivByK(int[] A, int K) {
        Map<Integer,Integer> hash = new HashMap<>();
        int ans = 0;
        int pre = 0;
        hash.put(0,1);
        for(int i = 0; i< A.length; i++){
            pre += A[i];
            //如果两个数的差能被K整除，就说明这两个数 mod K得到的结果相同，
            //只要找有多少对 mod k 相同的数就可以组合一下得到结果，
            //  java 取 mod 是取余 所以对于负数要处理
            int mod = (pre%K + K) % K;
            if(hash.containsKey(mod)){
                ans +=hash.get(mod);
            }
            hash.put(mod,hash.getOrDefault(mod,0)+1);
        }
        return ans;
    }
}
