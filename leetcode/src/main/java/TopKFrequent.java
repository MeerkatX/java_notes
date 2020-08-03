

import java.util.*;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/13
 * @Description: 前K个高频单词
 */
public class TopKFrequent {

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
}
