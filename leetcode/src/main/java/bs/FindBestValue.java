package bs;

import java.util.Arrays;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/14
 * @Description:
 */
public class FindBestValue {
    public static void main(String[] args) {

        /*
        [60864,25176,27249,21296,20204]
56803
         */


        System.out.println(new FindBestValue().findBestValue(new int[]{
                60864, 25176, 27249, 21296, 20204
        }, 56803));
        System.out.println(new FindBestValue().findBestValue(new int[]{
                2, 3, 5
        }, 11));
    }

    public int findBestValue(int[] arr, int target) {
        if (arr.length == 0) return 0;
        Arrays.sort(arr);
        int left = 0;
        int right = arr[arr.length - 1];
        int ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int sum = 0;
            for (int i : arr) {
                sum += i > mid ? mid : i;
            }
            if (sum > target) {
                right = mid - 1;
            } else {
                ans = mid;
                left = mid + 1;
            }
        }
        int sum = 0;
        for (int i : arr) {
            sum += i > ans ? ans : i;
        }
        int chooseSmall = sum;
        sum = 0;
        for (int i : arr) {
            sum += i > (ans + 1) ? (ans + 1) : i;
        }
        int chooseBig = sum;
        return Math.abs(chooseSmall - target) <= Math.abs(chooseBig - target) ? ans : ans + 1;
    }

}
