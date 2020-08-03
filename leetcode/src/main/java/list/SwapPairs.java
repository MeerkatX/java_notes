package list;

/**
 * @Auther: 徐少伟
 * @Date: 2020/4/29
 * @Description:
 */
public class SwapPairs {

    public static void main(String[] args) {
        ListNode ln1 = new ListNode(1);
        ListNode ln2 = new ListNode(2);
        ListNode ln3 = new ListNode(3);
        ListNode ln4 = new ListNode(4);
        ln1.next = ln2;
        ln2.next = ln3;
        ln3.next = ln4;

        new SwapPairs().swapPairs(ln1);
    }

    public ListNode swapPairs(ListNode head) {
        ListNode h = new ListNode(0);
        h.next = head;
        ListNode point = h;//
        while (head != null && head.next != null) {
            ListNode ln1 = head;
            ListNode ln2 = head.next;

            point.next = ln2; //将前一个与之后的列表串起来
            // 不串起来就会丢失 ln2 导致如 1->2->3->4 变成 1->3 因为 2->1(head)->3->4
            // 加上 0(point)->1(head)->2->3->4 变成 0(point)->2->1(head)->3->4
            // 之后 0->2->1(point)->4->3(head)
            ln1.next = ln2.next;
            ln2.next = ln1;

            point = ln1;//保存上一个节点指针，以便于之后将其串起来。
            head = ln1.next;//指针指向下一位
        }
        return h.next;
    }

}
