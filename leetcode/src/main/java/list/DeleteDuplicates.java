package list;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/29
 * @Description: 83. 删除排序链表中的重复元素
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/
 */
public class DeleteDuplicates {

    public static void main(String[] args) {
        Integer i = new Integer(1);
        System.out.println(System.identityHashCode(i) == System.identityHashCode(++i)
        );
        System.out.println(i.hashCode() == (++i).hashCode());
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(2);
//        l1.next = l2;
//        l2.next = l3;
        new DeleteDuplicates().deleteDuplicates3(l1);
    }

    public ListNode deleteDuplicates(ListNode head) {
        //计数法 时间复杂度高
        ListNode temp = head;
        HashMap<Integer, Integer> set = new HashMap<>();
        while (temp != null) {
            set.put(temp.val, set.getOrDefault(temp.val, 0) + 1);
            temp = temp.next;
        }
        ListNode hn = new ListNode(0);
        temp = head;
        ListNode point = hn;
        while (temp != null) {
            if (set.get(temp.val) == 1) {
                point.next = temp;
                point = point.next;
                point.next = null;
            }
            temp = temp.next;
        }
        return hn.next;
    }

    public ListNode deleteDuplicates2(ListNode head) {
        //双指针
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(-1);//需要一个头
        dummy.next = head;
        ListNode a = dummy;
        ListNode b = head;

        // 用 a.next.val 和 b.next.val比较 这样就可以跳过相同的了 a指向的永远是唯一的值

        while (b != null && b.next != null) {
            //初始化的时a指向的是哑结点，所以比较逻辑应该是a的下一个节点和b的下一个节点
            if (a.next.val != b.next.val) {
                a = a.next;
                b = b.next;
            } else {
                //如果a、b指向的节点值相等，就不断移动b，直到a、b指向的值不相等
                while (b != null && b.next != null && a.next.val == b.next.val) {
                    b = b.next;
                }
                a.next = b.next;
                b = b.next;
            }
        }
        return dummy.next;
    }

    public ListNode deleteDuplicates3(ListNode head) {
        // write code here
        if(head == null) return head;
        ListNode fake = new ListNode(0);
        ListNode p = fake;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null) {
            //如果两者相等，就往后移动
            while (fast!=null &&  fast.val == slow.val) {
                fast= fast.next;
            }
            //如果只移动了一格（next = fast）说明是独一无二的元素
            if(slow.next == fast){
                p.next = slow;
                p = p.next;
            }
            slow = fast;//继续往后比较
        }
        p.next = slow;//最后一个元素无法比较，单独处理
        return fake.next;
    }
}
