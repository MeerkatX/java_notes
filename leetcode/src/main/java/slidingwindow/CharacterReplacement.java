package slidingwindow;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/25
 * @Description:
 */
public class CharacterReplacement {

    public static void main(String[] args) {
        System.out.println(new CharacterReplacement().characterReplacement("AABABBA",1));
    }


    public int characterReplacement(String s, int k) {
        char[] chars = s.toCharArray();
        int left = 0;
        int right = 1;

        int ans = 0;
        int pos = 0;
        boolean f2 = true;
        char first = chars[0];
        int n = k;

        for (; right < chars.length; right++) {

            if (chars[right] != first) {
                if (f2) {
                    pos = right;
                    f2 = false;
                }
                n--;
                if (n < 0) {
                    ans = Math.max(ans, right - left);
                    first = chars[pos];
                    left = pos;
                    right = pos;
                    f2 = true;
                    n = k;

                }
            }
        }
        return Math.max(ans,right-left);
    }
}
