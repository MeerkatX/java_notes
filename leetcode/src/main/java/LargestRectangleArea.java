import java.util.Stack;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/30
 * @Description:
 */
public class LargestRectangleArea {
    public static void main(String[] args) {
        System.out.println(new LargestRectangleArea().largestRectangleArea(new int[]{
                2,1,5,6,4,5
        }));
    }

    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int max = 0;
        int[] h = new int[heights.length+2];
        h[0] = 0;
        h[h.length-1] = 0;
        for(int i=0;i<heights.length;i++){
            h[i+1] = heights[i];
        }
        for (int i = 0; i < h.length; i++) {
            while (!stack.isEmpty()&&h[stack.peek()]>h[i]){
                int cur = stack.pop();
                int left = stack.peek() + 1;
                int right = i-1;
                max = Math.max(max, (right-left+ 1) * h[cur]);
            }
            stack.push(i);
        }
        return max;
    }
}
