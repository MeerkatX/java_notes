/**
 * @Auther: 徐少伟
 * @Date: 2020/6/1
 * @Description:
 */
public class Multiply {
    public static void main(String[] args) {
        System.out.println(new Multiply().multiply("237", "284"));
    }


    public String multiply(String num1, String num2) {

        if (num1.equals("0") || num2.equals("0")) return "0";

        if (num1.length() < num2.length()) {
            String t = num2;
            num2 = num1;
            num1 = t;
        }
        int n2 = num2.length() - 1;

        String ans = null;
        while (n2 >= 0) {
            int n1 = num1.length() - 1;
            int in = 0;
            StringBuilder t = new StringBuilder();
            for (int i = 0; i < num2.length() - 1 - n2; i++) t.append(0);
            while (n1 >= 0) {
                int v = (num1.charAt(n1) - 48) * (num2.charAt(n2) - 48) + in;
                in = v / 10;
                if (n1 != 0) t.insert(0, v % 10);
                else t.insert(0, v);
                n1--;
            }
            n2--;

            if (ans == null) {
                ans = t.toString();
                continue;
            }
            StringBuilder pre = new StringBuilder();
            int al = ans.length() - 1;
            int tl = t.length() - 1;
            in = 0;
            while (al >= 0) {
                int v = (ans.charAt(al--) - 48) + (t.charAt(tl--) - 48) + in;
                in = v / 10;
                v = v % 10;
                pre.insert(0, v);
                if (al == -1) {
                    if (tl != -1) pre.insert(0, Integer.valueOf(t.substring(0, tl + 1)) + in);
                    if (tl == -1 && in != 0) pre.insert(0, in);
                }
            }

            ans = pre.toString();

        }
        return ans;
    }


    public String multiply2(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int[] res = new int[num1.length() + num2.length()];
        for (int i = num1.length() - 1; i >= 0; i--) {
            int n1 = num1.charAt(i) - '0';
            for (int j = num2.length() - 1; j >= 0; j--) {
                int n2 = num2.charAt(j) - '0';
                int sum = (res[i + j + 1] + n1 * n2);
                res[i + j + 1] = sum % 10;
                res[i + j] += sum / 10;
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            if (i == 0 && res[i] == 0) continue;
            result.append(res[i]);
        }
        return result.toString();
    }
}

