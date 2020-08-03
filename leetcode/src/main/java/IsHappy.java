

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/30
 * @Description:
 */
public class IsHappy {

    public boolean isHappy(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        while (!map.containsKey(n)) {
            map.put(n, 1);
            int sum = 0;
            while (n != 0) {
                sum += Math.pow(n % 10, 2);
                n = n / 10;
            }
            if (sum == 1)
                return true;
            n = sum;
        }
        return false;
    }
}
