
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/26
 * @Description:
 */
public class LargeGroupPositions {

    public List<List<Integer>> largeGroupPositions(String S) {
        List<List<Integer>> result = new ArrayList<>();
        int left = 0;
        int right = 1;
        while (right < S.length()) {
            List<Integer> list = new ArrayList<>();
            if (S.charAt(right) == S.charAt(left)) {
                right++;
            } else {
                if (right - left > 2) {
                    list.add(left);
                    list.add(right-1);
                    result.add(list);
                }
                left = right;
                right++;
            }
        }
        return result;
    }
}
