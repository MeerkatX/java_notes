package dfs;

import java.util.*;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/21
 * @Description:
 */
public class AvoidFlood {
    public static void main(String[] args) {
        Arrays.stream(new AvoidFlood().avoidFlood(new int[]{
                44857,62521,48500,0,35034,0,0,33033,0,0,7849,81143,0,0,0,21081,0,95613,
                0,55550,38386,65636,60167,0,62719,0,60123,0,0,19312,82045,58873,0,69059,
                42179,0,0,0,0,33581,37254,0,63250,0,0,40886,0,19127,0,0,76531,27002,61187,
                50971,40758,0,69975,0,0,0,0,30302,48607,80458,28988,34773,90388,0,68066,0,0,
                28319,35717,22882,55550,0,0,0,0,49594,0,0,20385,33033,23226,42518,0,28604,0,
                49594,47830,90643,0,0,69739,0,0,73089,38838,0,0,0,20079,0,44857,0,0,80458,0,
                41242,0,0,0,0,0,0,0,15319,2322,86494,0,33581,32499,34453,0,85750,0,0,18848,0,0,
                90388,0,0,0,0,12781,95613,0,0,68066,60123,81143,25577,64368,0,0,14125,4041,0,0,0,
                0,0,0,15882,0,9714,0,0,0,2322,85075,30671,62448,18716,0,0,0,0,48500,23226,0,0,38444,
                0,18716,0,0,0,0,15882,42456,0,58873,0,0,60547,69059,0,28319,50971,0,28604,32499,
                47830,60547,24713,38386,0,80709,0,40886,30647,69975,0,38444,0,40758,0,69739,86494,
                0,0,0,79315,30302,62521,73318,0,0,0,0,0,0,88056,70495,48897,0,30671,0,0,0,35717
                ,62448,0,63112,0,8714,48897,96901,0,96901,0,0,0,0,70495,0,82045,0,0,38838,99251,
                5534,63250,0,62719,25191,0,48607,79315,34453,22882,0,0,0,0,0,18718,0,0,0,48349,0,
                69976,0,76531,14125,0,50861,27002,42518,5534,12781,0,0,0,0,30647,0,24713,0,5600,
                0,0,0,0,0,0,0,19127,0,20385,0,63112,0,0,9714,5600,28988,50861,0,60167,0,0,73089,
                18848,0,0,85750,0,0,85075,0,0,41242,0,25191,0,25577,0,87049,42456,87049,48349,73318,
                61187,0,37254,0,65636,0,0,0,0,8714,0,0,0,42179,0,0,35034,0,20079,0,13855,21081,0,90643,
                69976,80709,34773,88056,19312,0,0,0,0,99251,0,7849,64368,18718,0,0,15319,13855,4041,0,0,0
        })).forEach(System.out::println);
    }

    public int[] ans;
    public HashSet<Integer> set = new HashSet<>();


    public int[] avoidFlood(int[] rains) {
        ans = new int[rains.length];
        System.out.println(rains.length);
        boolean can = dfs(rains, 0);
        if (can) return ans;
        else return new int[0];
    }

    public boolean dfs(int[] rains, int start) {
        if (start >= rains.length) return true;

        if (rains[start] == 0) {
            if (set.size() == 0) {
                ans[start] = 1;
                return dfs(rains, start + 1);
            }
            HashSet<Integer> setcpy = new HashSet<>(set);
            for (int i:setcpy){
                set.remove(i);
                ans[start] = i;
                if (dfs(rains, start + 1)) return true;
                set.add(i);
            }
            return false;
        } else {
            if (set.contains(rains[start])) return false;
            set.add(rains[start]);
            ans[start] = -1;
            return dfs(rains, start + 1);
        }
    }
}
