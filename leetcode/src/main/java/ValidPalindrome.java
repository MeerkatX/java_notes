

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/19
 * @Description:
 */
public class ValidPalindrome {

    public static void main(String[] args) {
        if (new ValidPalindrome().validPalindrome("abeeca")) System.out.println("t");
        else System.out.println("f");
    }

    public boolean validPalindrome(String s) {
        int left = 0,right = s.length()-1, n = 0;
        while(left < right){
            if(s.charAt(left) != s.charAt(right)) {
                if(n==0) right--;
                if(n==1){
                    right++;
                    left++;
                }
                if(n>1) return false;
                n++;
            }else{
                left++;
                right--;
            }
        }
        return true;
    }

}
