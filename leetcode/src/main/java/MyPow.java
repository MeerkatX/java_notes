

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/23
 * @Description:
 */
public class MyPow {

    public static void main(String[] args) {
        MyPow myPow = new MyPow();
        int i = -2147483648;//这里会超
        System.out.println((-i));
        System.out.println(myPow.myPow(2,-2147483648));
    }

    public double myPow(double x, int n) {
        if (x == 0)
            return 0;
        double result = pow(x, n);
        return n > 0 ? result : 1 / result;
    }

    private double pow(double x, int n) {
        if (n == 0)
            return 1.0;
        double half = pow(x, n / 2);
        half = half * half;
        //-2147483648 如果用 n%2==1会算错
        if ((n & 1) == 1)
            half = half * x;
        return half;
    }
}
