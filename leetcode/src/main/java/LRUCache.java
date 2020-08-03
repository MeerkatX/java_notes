
import java.util.*;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/21
 * @Description:
 *  LRU (最近最少使用) 缓存机制
 */
public class LRUCache {
    //简易LinkedHashMap(最近最少)实现 双链表 + 哈希表

    private DLinkedNode head, tail;

    private int capacity;

    private int size;

    //哈希表把所有的节点都保存下来，这样就只需要O(1)的时间就能找到节点 不需要遍历列表来找节点
    private Hashtable<Integer, DLinkedNode> hashtable = new Hashtable<>();


    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev, next;
    }

    private void addNode(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        DLinkedNode p = node.prev;
        DLinkedNode n = node.next;

        p.next = n;
        n.prev = p;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addNode(node);
    }

    private DLinkedNode popTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }


    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;

        this.head = new DLinkedNode();
        this.tail = new DLinkedNode();
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    public int get(int key) {
        DLinkedNode node = hashtable.get(key);
        if (node == null) return -1;
        moveToHead(node);//每当用到该元素就移动到头部，那么尾部一定是最久未使用
        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = hashtable.get(key);
        if (node == null) {
            node = new DLinkedNode();
            node.key = key;
            node.value = value;
            hashtable.put(key, node);
            addNode(node);//每次添加都添加到头部，那么最早添加的就在尾部
            size++;
            if (size > capacity) {
                DLinkedNode tail = popTail();
                hashtable.remove(tail.key);
                --size;
            }
        } else {
            node.value = value;
            moveToHead(node);
        }
    }
}
