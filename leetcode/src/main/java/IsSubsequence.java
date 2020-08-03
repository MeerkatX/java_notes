

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/27
 * @Description:
 * 判断子序列
 */
public class IsSubsequence {

    public boolean isSubsequence(String s, String t) {
        int sIndex = 0;
        int tIndex = 0;
        while(tIndex < t.length() && sIndex < s.length()){
            if(s.charAt(sIndex)==t.charAt(tIndex)){
                sIndex++;
            }
            tIndex++;
        }
        return sIndex == s.length();
    }

}
