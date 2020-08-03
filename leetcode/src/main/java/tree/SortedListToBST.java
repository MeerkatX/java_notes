package tree;

import list.ListNode;

import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/4
 * @Description:
 */
public class SortedListToBST {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(-10);
        l1.next = new ListNode(-3);
        l1.next.next = new ListNode(0);
        l1.next.next.next = new ListNode(5);
        l1.next.next.next.next = new ListNode(9);
        new SortedListToBST().sortedListToBST(l1);
    }

    public TreeNode sortedListToBST(ListNode head) {
        return sortedArrayToBSTHelper(head);
    }

    public TreeNode sortedArrayToBSTHelper(ListNode head) {
        if(head==null) return null;
        if (head.next == null) return new TreeNode(head.val);
        ListNode slow = head;
        ListNode fast = head;
        ListNode pre = slow;
        while(fast.next != null){
            pre = slow;
            slow = slow.next;
            if (fast.next.next!=null) fast = fast.next.next; else fast = fast.next;
        }
        pre.next = null;
        TreeNode root = new TreeNode(slow.val);
        root.left = sortedArrayToBSTHelper(head);
        root.right = sortedArrayToBSTHelper(slow.next);
        return root;
    }
}
