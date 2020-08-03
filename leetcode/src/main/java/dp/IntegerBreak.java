package dp;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/23
 * @Description:
 */
public class IntegerBreak {
    public static void main(String[] args) {
        IntegerBreak integerBreak = new IntegerBreak();
        System.out.println(integerBreak.integerBreak(9));
    }


    public int integerBreak(int n) {
        int[] max = new int[n + 1];
        max[2] = 1;
        max[3] = 2;
        for (int i = 4; i <= n; i++) {
            int m = 0;
            for (int j = 2; j <= i / 2; j++) {
                max[i] = Math.max(max[j], j) * Math.max(max[i - j], i - j);
                if (m < max[i])
                    m = max[i];
            }
            max[i] = m;
        }
        return max[n];
    }
}
