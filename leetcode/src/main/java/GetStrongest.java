import java.util.Arrays;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/7
 * @Description:
 */
public class GetStrongest {

    public int[] getStrongest(int[] arr, int k) {
        Arrays.sort(arr);
        int length = arr.length;
        int mid = arr[((length - 1) / 2)];

        int[] ans = new int[k];
        int l = 0;
        int r = length - 1;
        for (int i = 0; i < k; i++) {
            if (Math.abs(arr[l] - mid) > Math.abs(arr[r] - mid)) ans[i] = arr[l++];
            else ans[i] = arr[r--];
        }
        return ans;
    }

}
