
/**
 * @Auther: 徐少伟
 * @Date: 2020/5/14
 * @Description:
 */
public class MyAiot {

    public int myAtoi(String str) {
        int start = 0,end = 0;
        for(;end<str.length();end++){
            if(str.charAt(end)=='-' && start == 0) start = end;
            if('0'<=str.charAt(end) && str.charAt(end)<='9'){
                if(start == 0) start = end;
            }else{
                if(start != 0) break;
            }
        }
        StringBuilder stringBuilder =new StringBuilder();

        long num = Long.valueOf(str.substring(start,end));
        return (int) (num>0?Math.max(num,Integer.MIN_VALUE):Math.min(num,Integer.MAX_VALUE));
    }
}
