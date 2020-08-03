import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/7
 * @Description: 127. 单词接龙
 */
public class LadderLength {

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Deque<String> deque = new ArrayDeque<>();
        int min = Integer.MAX_VALUE;
        deque.add(beginWord);
        while (!deque.isEmpty()) {
            String word = deque.pollFirst();
            for (int i = 0; i < word.length(); i++) {
                for (int j = 0; j < wordList.size(); j++) {
                    String w = wordList.get(j);
                    if(w.replace(w.charAt(i),word.charAt(i)).equals(word)){
                        deque.addLast(w);
                        min++;
                    }
                }
            }

        }
        return min;

    }


}
