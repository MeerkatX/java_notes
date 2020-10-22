import java.util.*;

/**
 * @ClassName: CommonChars
 * @Auther: MeerkatX
 * @Date: 2020-10-14 09:41
 * @Description:
 */
public class CommonChars {

    public static void main(String[] args) {
        new CommonChars().commonChars(new String[]{"bella","label","roller"}).forEach(System.out::println);
    }

    public List<String> commonChars(String[] A) {
        int[] ws = new int[26];
        Arrays.fill(ws,Integer.MAX_VALUE);
        for (int i = 0; i < A.length; i++) {
            int[] temp = new int[26];
            for (int j = 0; j < A[i].length(); j++) {
                temp[A[i].charAt(j) - 'a'] += 1;
            }
            for (int j = 0; j < temp.length; j++) {
                ws[j] = Math.min(ws[j], temp[j]);
            }
        }
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < ws.length; i++) {
            for (int j = 0; j < ws[i]; j++) {
                ans.add(String.valueOf((char) ('a' + i)));
            }
        }
        return ans;
    }
}
