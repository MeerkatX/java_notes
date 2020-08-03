package list;

import list.ListNode;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/26
 * @Description:
 */
public class MergeKLists {

    public ListNode mergeKLists(ListNode[] lists) {
        return mergeKLists(lists, 0, lists.length-1);
    }

    public ListNode mergeKLists(ListNode[] lists, int start, int end) {
        if (end - start == 1)
            return lists[start];
        int mid = start + (end - start) / 2;
        ListNode list1 = mergeKLists(lists, start, mid);
        ListNode list2 = mergeKLists(lists, mid + 1, end);
        return mergeHelper(list1, list2);
    }

    public ListNode mergeHelper(ListNode listNode1, ListNode listNode2) {
        ListNode head = new ListNode(0);
        ListNode point = head;
        while (listNode1 != null && listNode2 != null) {
            if (listNode1.val < listNode2.val) {
                point.next = listNode1;
                listNode1 = listNode1.next;
            } else {
                point.next = listNode2;
                listNode2 = listNode2.next;
            }
            point = point.next;
        }
        point.next = listNode1 == null ? listNode2 : listNode1;
        return head.next;
    }

}
