

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/26
 * @Description:
 */
public class NumberOfArithmeticSlices {
    public int numberOfArithmeticSlices(int[] A) {
        int num = 0;
        for (int i = 0; i < A.length - 1; i++) {
            int length = 0;
            int c = A[i + 1] - A[i];
            for (int j = i + 1; j < A.length; j++) {
                if (A[j] - A[j-1] == c) {
                    length++;
                    if (length > 3)
                        num++;
                }else
                    break;
            }
        }
        return num;
    }
}
