import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/14
 * @Description:
 */
public class FindLeastNumOfUniqueInts {

    public static void main(String[] args) {
        System.out.println(new FindLeastNumOfUniqueInts().findLeastNumOfUniqueInts(new int[]{
                24, 119, 157, 446, 251, 117, 22, 168, 374, 373, 323, 311, 441, 213, 120, 412, 200, 236, 328, 24, 164, 104, 331, 32, 19, 223, 89, 114, 152, 82, 456, 381, 355, 343, 157, 245, 443, 368, 229, 49, 82, 16, 373, 142, 240, 125, 8
        }, 41));

    }

    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        Arrays.sort(arr);
        int[] total = new int[arr.length];
        total[0] = 1;
        int p = 0;
        for (int i = 1; i < arr.length - 1; i++) {

            if (arr[i] != arr[i - 1]) {
                p++;
                total[p] = 1;
                continue;
            }

            total[p]++;
        }
        p=p+1;
        Arrays.sort(total, 0, p);
        int j = 0;
        while (k > 0) {
            k -= total[j++];
        }
        return k < 0 ? p - j + 2 : p - j + 1;
    }

}
