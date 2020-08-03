

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/24
 * @Description: 丑数2 可用动态规划 或 堆(优先队列)来做
 */
public class NthUglyNumber {
    public int nthUglyNumber(int n) {
        int[] UglyMumber = new int[n];
        UglyMumber[0] = 1;
        int i2 = 0, i3 = 0, i5 = 0;
        for (int i = 0; i < n; i++) {
            UglyMumber[i] = Math.min(Math.min(UglyMumber[i2] * 2, UglyMumber[i3] * 3), UglyMumber[i5] * 5);
            if (UglyMumber[i] == UglyMumber[i2] * 2)
                i2++;
            if (UglyMumber[i] == UglyMumber[i3] * 3)
                i3++;
            if (UglyMumber[i] == UglyMumber[i5] * 5)
                i5++;
        }
        return UglyMumber[n - 1];
    }
}
