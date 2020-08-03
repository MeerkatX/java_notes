import java.util.LinkedList;
import java.util.Stack;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/3
 * @Description:
 */
public class EvalRPN {
    public static void main(String[] args) {
        new EvalRPN().evalRPN(new String[]{"2", "1", "+", "3", "*"});
    }

    public int evalRPN(String[] tokens) {
        LinkedList<String> stack = new LinkedList<>();
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("+") || tokens[i].equals("-") || tokens[i].equals("*") || tokens[i].equals("/")) {
                int b = Integer.valueOf(stack.pop());
                int a = Integer.valueOf(stack.pop());
                int result = 0;
                switch (tokens[i]) {
                    case "*":
                        result = a * b;
                        break;
                    case "+":
                        result = a + b;
                        break;
                    case "/":
                        result = a / b;
                        break;
                    case "-":
                        result = a - b;
                        break;
                }
                stack.push(String.valueOf(result));
            } else {
                stack.push(tokens[i]);
            }
        }
        return Integer.valueOf(stack.peek());
    }

    public static int evalRPN2(String[] tokens) {
        //用数组模拟栈
        int[] numStack = new int[tokens.length / 2 + 1];
        int index = 0;
        for (String s : tokens) {
            switch (s) {
                case "+":
                    numStack[index - 2] += numStack[--index];
                    //这里numStack[index -2]刚好是前一个数字，而且当计算完毕后要push进栈的位置
                    break;
                case "-":
                    numStack[index - 2] -= numStack[--index];
                    break;
                case "*":
                    numStack[index - 2] *= numStack[--index];
                    break;
                case "/":
                    numStack[index - 2] /= numStack[--index];
                    break;
                default:
                    // numStack[index++] = Integer.valueOf(s);
                    //valueOf改为parseInt，减少自动拆箱装箱操作
                    numStack[index++] = Integer.parseInt(s);
                    break;
            }
        }
        return numStack[0];
    }

}
