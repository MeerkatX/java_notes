import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @ClassName: MyStack
 * @Auther: MeerkatX
 * @Date: 2020-09-19 21:21
 * @Description:
 */
public class MyStack {

    private Deque<Integer> q1 = new ArrayDeque<>();
    private Deque<Integer> q2 = new ArrayDeque<>();
    private int top;

    /**
     * Initialize your data structure here.
     */
    public MyStack() {

    }

    /**
     * Push element x onto stack.
     */
    public void push(int x) {
        q1.addLast(x);
        top = x;
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    public int pop() {
        while (q1.size() > 1) {
            top = q1.poll();
            q2.addLast(top);
        }
        top = q1.poll();
        Deque<Integer> temp = q2;
        q2 = q1;
        q1 = temp;
        return top;
    }

    /**
     * Get the top element.
     */
    public int top() {
        return top;
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return q1.isEmpty();
    }
}
