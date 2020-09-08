

import javafx.util.Pair;

import java.util.*;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/13
 * @Description: 前K个高频单词
 * 347. 前 K 个高频元素
 * https://leetcode-cn.com/problems/top-k-frequent-elements/
 */
public class TopKFrequent {

    public static void main(String[] args) {
        int[] res = new TopKFrequent().topKFrequent(new int[]{
                1,1,1,2,2,3
        },2);
        System.out.println();
    }

    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> w = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            if (w.containsKey(words[i])) {
                w.put(words[i], 0);
            } else {
                w.put(words[i], w.get(words[i] + 1));
            }
        }
        List<String> keys = new ArrayList<>(w.keySet());
        Collections.sort(keys, (o1, o2) -> w.get(o1).equals(w.get(o2)) ?
                o1.compareTo(o2) : w.get(o2) - w.get(o1));
        //比较keys，统计量是否相同，相同就比较两个字符串，不同就比较谁统计量大
        return keys.subList(0, k);
    }

    public int[] topKFrequent(int[] nums, int k) {
        if (nums.length == 0) return new int[k];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) iterator.next();
            queue.add(entry);
        }
        int[] res = new int[k];
        for (int i=0; i<k;i++){
            res[i]=queue.poll().getKey();
        }
        return res;
    }
}
