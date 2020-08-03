
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/12
 * @Description:
 */
public class IsPalindrome {

    public static void main(String[] args) {
        new IsPalindrome().isPalindrome("0P");
    }

    public boolean isPalindrome(String s) {
        int left =0; int right = s.length()-1;
        while(left < right){
            while( left<s.length() && (!((s.charAt(left)<='Z' && s.charAt(left)>='A')
                    || (s.charAt(left)<='z' && s.charAt(left)>='a')
                    || (s.charAt(left)>='0' && s.charAt(left)<='9')))){
                left++;
            }
            while(right>=0 && (!((s.charAt(right)<='Z' && s.charAt(right)>='A')
                    || (s.charAt(right)<='z' && s.charAt(right)>='a')
                    || (s.charAt(right)>='0' && s.charAt(right)<='9')))){
                right--;
            }

            if(left>right) return true;
            if(Character.toLowerCase(s.charAt(left))!=Character.toLowerCase(s.charAt(right))) return false;
            else {
                left++;
                right--;
            }

        }
        return true;
    }

    public boolean isPalindrome2(String s) {
        int left =0; int right = s.length()-1;
        char[] cs = s.toCharArray();
        while(left<right){
            while(!Character.isLetterOrDigit(cs[left])) left++;
            while(!Character.isLetterOrDigit(cs[right])) right--;
            if (Character.toLowerCase(cs[left])!=Character.toLowerCase(cs[right])) return false;
        }
        return true;
    }
}
