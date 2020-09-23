package collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/7
 * @Description:
 */
public class MyCollection {
    public static void main(String[] args) {
        LinkedList<Integer> in = new LinkedList<>();

        new ArrayList<>();
        new HashMap<>();
        new HashSet<>();
        new TreeSet<>();
        new TreeMap<>();
        new LinkedHashMap<>();
        new PriorityQueue<>();
        new ConcurrentHashMap<>();

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 16; i++) {
            System.out.println(i);
            map.put(i, i);
        }
    }
}
