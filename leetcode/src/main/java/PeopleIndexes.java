

import java.util.*;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/19
 * @Description:
 */
public class PeopleIndexes {

    public static void main(String[] args) {
        List<List<String>> tt = new ArrayList<>();
        List<String> tp = new ArrayList<>();
        tp.add("leetcode");

    }

    public List<Integer> peopleIndexes(List<List<String>> favoriteCompanies) {
        List<Integer> ans = new ArrayList<>();
        //使用Set.containAll方法
        for (int i = 0; i < favoriteCompanies.size(); ++i) {
            List<String> one = favoriteCompanies.get(i);
            boolean flag = true;
            for (int j = 0; j < favoriteCompanies.size(); ++j) {
                if (i == j) continue;
                List<String> another = favoriteCompanies.get(j);
                HashSet<String> set = new HashSet<>(another);
                if (set.containsAll(one)) {
                    flag = false;
                    break;
                }
            }
            if (flag) ans.add(i);
        }
        return ans;
    }


    public List<Integer> peopleIndexesE(List<List<String>> favoriteCompanies) {
        //错误方法，暂留供以后查看
        for (int i = 0; i < favoriteCompanies.size(); i++) {
            Collections.sort(favoriteCompanies.get(i));
            favoriteCompanies.get(i).add(i + "");
        }
        Collections.sort(favoriteCompanies, (a, b) -> {
            int l1 = a.size();
            int l2 = b.size();
            int min = Math.min(l1, l2);
            for (int i = 0; i < min; i++) {
                return a.get(i).compareTo(b.get(i));
            }
            return l1 - l2;
        });
        List<Integer> ans = new ArrayList<>();
        int idx = 0;
        while (!favoriteCompanies.isEmpty()) {
            List<List<String>> t = new ArrayList<>(favoriteCompanies);
            for (int i = 0; i < favoriteCompanies.size(); i++) {
                if (i == favoriteCompanies.get(i).size() - 1) t.remove(i);
                if (i > 0 && i < favoriteCompanies.size() - 1 &&
                        !favoriteCompanies.get(i).get(idx).equals(favoriteCompanies.get(i - 1).get(idx)) &&
                        !favoriteCompanies.get(i).get(idx).equals(favoriteCompanies.get(i + 1).get(idx))) {
                    ans.add(Integer.valueOf(favoriteCompanies.get(i).get(favoriteCompanies.size() - 1)));
                    t.remove(i);
                }
                if (i == 0 && !favoriteCompanies.get(0).get(idx).equals(favoriteCompanies.get(1).get(idx))) {
                    ans.add(Integer.valueOf(favoriteCompanies.get(0).get(favoriteCompanies.size() - 1)));
                    t.remove(i);
                }
                if (i == favoriteCompanies.size() - 1 && !favoriteCompanies.get(i).get(idx).equals(favoriteCompanies.get(i - 1).get(idx))) {
                    ans.add(Integer.valueOf(favoriteCompanies.get(i).get(favoriteCompanies.size() - 1)));
                    t.remove(i);
                }
            }
            favoriteCompanies = t;
        }
        return ans;
    }

}
