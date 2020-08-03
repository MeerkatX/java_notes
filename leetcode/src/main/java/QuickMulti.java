/**
 * @Auther: 徐少伟
 * @Date: 2020/6/2
 * @Description:
 * 快速乘 俄罗斯农民算法
 */
public class QuickMulti {

    public static void main(String[] args) {
        System.out.println(new QuickMulti().quickMulti(4,5));
    }

    public int quickMulti(int a,int b){
        int ans = 0;
        for(;b>0;b>>=1){
            if ((b&1)>0){
                ans+=a;
            }
            a<<=1;
        }
        return ans;
    }

}
