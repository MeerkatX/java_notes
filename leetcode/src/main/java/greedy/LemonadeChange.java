package greedy;

/**
 * @ClassName: LemonadeChange
 * @Auther: MeerkatX
 * @Date: 2020-09-16 20:13
 * @Description: 860. 柠檬水找零 easy
 * https://leetcode-cn.com/problems/lemonade-change/
 */
public class LemonadeChange {

    public boolean lemonadeChange(int[] bills) {
        int[] money = new int[3];
        for (int i = 0; i < bills.length; i++) {
            if (bills[i] == 5) {
                money[0]++;
            } else if (bills[i] == 10) {
                int five = --money[0];
                if (five < 0) return false;
                money[1]++;
            } else if (bills[i] == 20) {
                int ten = money[1] - 1;
                int five = money[0] - 1;
                if (ten < 0) {
                    if (five - 2 < 0) return false;
                    else money[0] -= 3;
                } else {
                    if (five < 0) return false;
                    else {
                        money[0]--;
                        money[1]--;
                    }
                }
                money[2]++;
            }
        }
        return true;
    }

    public boolean lemonadeChange2(int[] bills) {
        int five = 0, ten = 0;
        for (int bill: bills) {
            if (bill == 5)
                five++;
            else if (bill == 10) {
                if (five == 0) return false;
                five--;
                ten++;
            } else {
                if (five > 0 && ten > 0) {
                    five--;
                    ten--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }

        return true;
    }

}
