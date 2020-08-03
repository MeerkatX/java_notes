
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: 徐少伟
 * @Date: 2019/11/28
 * @Description: 剑指offer部分代码
 */
public class Solution {

    //---------------------矩阵从左到右，从上到下递增 查找内部元素----------------------

    public boolean Find(int target, int[][] array) {
        int oneLength = array[0].length;
        int twoLength = array.length;
        int indexOne = twoLength - 1;
        int indexTwo = 0;
        while (indexOne < oneLength && indexTwo >= 0) {
            if (target == array[indexOne][indexTwo]) return true;
            if (target > array[indexOne][indexTwo])
                indexOne += 1;
            else
                indexTwo -= 1;
        }
        return false;
    }

    //-----------------------------------替换空格为 %20--------------------------------

    public String replaceSpace(StringBuffer str) {
//        str.toString().replaceAll(); 与 replace() 区别 前者正则，后者不是
        StringBuffer re = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == ' ') {
                re.append("%20");
            } else {
                re.append(c);
            }
        }
        return re.toString();
    }

    //-------------------------------从尾到头添加到arrayList---------------------------

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        //用栈保存再添加到list 可以，会开辟新空间 也可以利用递归(思想同栈)
        ListNode pre = null;
        ListNode next = null;
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (listNode != null) {
            ListNode temp = listNode;
            next = listNode.next;
            temp.next = pre;
            pre = temp;
            listNode = next;
        }
        while (pre != null) {
            arrayList.add(pre.val);
            pre = pre.next;
        }
        return arrayList;
    }

    class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    //-----------------------------------重建二叉树------------------------------------

    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
    /*
        //栈实现，暂未实现
        Stack<Integer> stack = new Stack<>();
        Stack<TreeNode> treeNodeStack = new Stack<>();
        TreeNode treeNode = new TreeNode(pre[0]);
        for (int i = 0; i < pre.length; i++) {
            for (int j = 0; j < in.length; j++) {
                if (in[j] == pre[i]) {
                    if (j < stack.peek()) {
                        treeNode.left = new TreeNode(in[j]);
                        treeNode = treeNode.left;
                    } else {
                        treeNode.right = new TreeNode(in[j]);
                        treeNode = treeNode.right;
                    }
                    stack.push(j);
                    treeNodeStack.push(treeNode);
                    break;
                }
            }
        }
        return treeNode;
     */


        // 递归
        if (pre.length == 0 || in.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(pre[0]);
        // 在中序中找到前序的根
        for (int i = 0; i < in.length; i++) {
            if (in[i] == pre[0]) {
                // 左子树，注意 copyOfRange 函数，左闭右开
                root.left = reConstructBinaryTree(Arrays.copyOfRange(pre, 1, i + 1), Arrays.copyOfRange(in, 0, i));
                // 右子树，注意 copyOfRange 函数，左闭右开
                root.right = reConstructBinaryTree(Arrays.copyOfRange(pre, i + 1, pre.length), Arrays.copyOfRange(in, i + 1, in.length));
                break;
            }
        }
        return root;
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    //-------------------------------用两个栈实现队列----------------------------------

    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    //------------------------------旋转数组的最小数字---------------------------------

    public int minNumberInRotateArray(int[] array) {
        //暴力循环
        if (array.length == 0)
            return 0;
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public int minNumberInRotateArray2(int[] array) {
        // 暴力方法小改
        if (array.length == 0)
            return 0;
        int min = array[array.length - 1];
        for (int i = array.length - 2; i >= 0; i--) {
            if (array[i] <= min) {
                min = array[i];
            } else {
                break;
            }
        }
        return min;
    }

    public int minNumberInRotateArray3(int[] array) {
        int length = array.length;
        //二分查找
        if (length == 1) {
            return array[0];
        }
        int left = 0;
        int right = length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[left] < array[right])
                return array[left];
            if (array[mid] > array[mid + 1])
                return array[mid + 1];
            if (array[mid] < array[mid - 1])
                return array[mid];
            if (array[mid] > array[left]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return 0;
    }

    //-------------------------------------------斐波那契数列----------------------------------

    public int Fibonacci(int n) {
        //最简单的暴力循环(动态规划)
        int n1 = 1;
        int n2 = 1;
        if (n == 0)
            return 0;
        for (int i = 2; i < n; i++) {
            int temp = n2 + n1;
            n1 = n2;
            n2 = temp;
        }
        return n2;
    }

    public int Fibonacci2(int n) {
        //暴力递归
        if (n <= 1) {
            return n;
        }
        return Fibonacci2(n - 2) + Fibonacci2(n - 1);
    }

    //--------------------------------------跳台阶--------------------------------------------

    public int JumpFloor(int target) {
        // 同理 暴力递归
        if (target <= 2)
            return target;
        return JumpFloor(target - 1) + JumpFloor(target - 2);
    }

    public int JumpFloor2(int target) {
        //简单暴力递归(动态规划)
        if (target <= 2)
            return target;
        int n1 = 1;
        int n2 = 2;
        for (int i = 2; i < target; i++) {
            int temp = n2 + n1;
            n1 = n2;
            n2 = temp;
        }
        return n2;
    }

    //-------------------------------------变态跳台阶------------------------------------------

    public int JumpFloorII(int target) {
        //迭代
        if (target == 1)
            return 1;
        if (target == 2)
            return 2;
        int[] method = new int[target + 1];
        method[1] = 1;
        method[2] = 2;
        for (int i = 3; i <= target; i++) {
            for (int j = 1; j < i; j++)
                method[i] += method[j];//这里可以这么理解 因为可以跳上任意台阶那么只要n-1,n-2,n-3再+1步跳上
            method[i] += 1;
        }
        return method[target];
    }

    public int JumpFloorII2(int target) {
        //利用数学公式归纳  f(n) = f(n-1)+f(n-2)+……f(1)
        //f(n) = 2f(n-1)
        int n = 1;
        for (int i = 2; i <= target; i++)
            n = 2 * n;
        return n;
    }

    //---------------------------------------矩形覆盖-------------------------------------------

    public int RectCover(int n) {
        //类似斐波那契 跳台阶 不是1就是2 即等于前两项和 
        int n1 = 1;
        int n2 = 2;
        if (n <= 2)
            return n;
        for (int i = 3; i <= n; i++) {
            int temp = n2 + n1;
            n1 = n2;
            n2 = temp;
        }
        return n2;
    }

    //------------------------------------二进制中的1的个数--------------------------------------

    /**
     * 左移<<补 0
     * 右移>>补 0 或 1（为负数时）
     * 无符号右移 >>> 首位补 0
     * 注意，没有 <<< 无符号左移操作
     */
    public void binaryLab(int n) {
        System.out.println(Integer.toBinaryString(n >> 1));
        System.out.println(Integer.toBinaryString(n << 1));
        System.out.println(Integer.toBinaryString(n >>> 1));
        n = -n;
        System.out.println(Integer.toBinaryString(n >> 1));
        System.out.println(Integer.toBinaryString(n << 1));
        System.out.println(Integer.toBinaryString(n >>> 1));//补0导致少一位
        System.out.println();
    }

    public int NumberOf1(int n) {
        //投机取巧利用api
        String binary = Integer.toBinaryString(n);
        int sum = 0;
        for (int i = 0; i < binary.length(); i++) {
            sum += binary.charAt(i) - 48;
            // 0 char 为48
        }
        return sum;
    }

    public int NumberOf1_2(int n) {
        //利用 1 不停左移，并且与 n ,如果得到 0，这说明这位不是 1
        int count = 0;
        int and = 1;
        while (and != 0) {
            if ((n & and) != 0)
                count++;
            and = and << 1;
        }
        return count;
    }

    public int NumberOf1_3(int n) {
        /**
         * 举个例子：一个二进制数1100，从右边数起第三位是处于最右边的一个1。
         * 减去1后，第三位变成0，它后面的两位0变成了1，而前面的1保持不变，因此得到的结果是1011.
         * 我们发现减1的结果是把最右边的一个1开始的所有位都取反了。
         * 这个时候如果我们再把原来的整数和减去1之后的结果做与运算，从原来整数最右边一个1那一位开始所有位都会变成0。
         * 如1100&1011=1000.也就是说，把一个整数减去1，再和原整数做与运算，会把该整数最右边一个1变成0.
         * 那么一个整数的二进制有多少个1，就可以进行多少次这样的操作。
         */
        int count = 0;
        while (n != 0) {
            count++;
            n = n & (n - 1);
        }
        return count;
    }

    //--------------------------------------最长回文子串-----------------------------------------
    public String longestPalindrome(String s) {
        //暴力循环 n^3
        if (s.length() < 2)
            return s;
        String longestPalindrome = null;
        int maxLength = 1;
        for (int i = 0; i < s.length() - 1; i++) {
            for (int j = i + 1; j < s.length(); j++) {
                int pre = i;
                int after = j;
                while (pre < after) {
                    if (s.charAt(pre) == s.charAt(after)) {
                        pre++;
                        after--;
                    } else {
                        break;
                    }
                }
                if (pre >= after && j - i + 1 > maxLength) {
                    maxLength = j - i + 1;
                    longestPalindrome = s.substring(i, j + 1);
                }
            }
        }
        return maxLength == 1 ? String.valueOf(s.charAt(0)) : longestPalindrome;
    }

    public String longestPalindrome2(String s) {
        //动态规划(还有马拉车，中心等方法)
        return null;
    }

    //----------------------------------盛最多水的容器------------------------------------------

    public int maxArea(int[] height) {
        //暴力法
        int maxArea = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = height.length - 1; j > i; j--) {
                int area = Math.min(height[j], height[i]) * (j - i);
                if (maxArea < area)
                    maxArea = area;
            }
        }
        return maxArea;
    }

    public int maxArea2(int[] height) {
        //双指针法
        /**
         * 这种方法背后的思路在于，两线段之间形成的区域总是会受到其中较短那条长度的限制。
         * 此外，两线段距离越远，得到的面积就越大。
         * 我们在由线段长度构成的数组中使用两个指针，一个放在开始，一个置于末尾。
         * 此外，我们会使用变量 maxareamaxarea 来持续存储到目前为止所获得的最大面积。
         * 在每一步中，我们会找出指针所指向的两条线段形成的区域，
         * 更新 maxareamaxarea，并将指向较短线段的指针向较长线段那端移动一步。
         */
        int pre = 0;
        int after = height.length - 1;
        int maxArea = 0;
        while (pre < after) {
            maxArea = Math.max(Math.min(height[pre], height[after]) * (after - pre), maxArea);
            if (height[after] > height[pre])
                pre++;
            else
                after--;
        }
        return maxArea;
        /**
         最初我们考虑由最外围两条线段构成的区域。现在，为了使面积最大化，我们需要考虑更长的两条线段之间的区域。
         如果我们试图将指向较长线段的指针向内侧移动，矩形区域的面积将受限于较短的线段而不会获得任何增加。
         但是，在同样的条件下，移动指向较短线段的指针尽管造成了矩形宽度的减小，但却可能会有助于面积的增大。
         因为移动较短线段的指针会得到一条相对较长的线段，这可以克服由宽度减小而引起的面积减小。
         */
    }

    //-----------------------------------字符串的排列-------------------------------------------

//    public ArrayList<String> Permutation(String str) {
//
//    }
//
//    private String permutation(String str,)
}
