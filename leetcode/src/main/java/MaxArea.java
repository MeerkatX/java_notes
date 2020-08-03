import java.util.Arrays;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/31
 * @Description:
 */
public class MaxArea {

    public static void main(String[] args) {
        System.out.println(new MaxArea().maxArea(5, 4, new int[]{1, 2, 4}, new int[]{1, 3}));
    }

    public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        long maxh = 0;
        long maxw = 0;
        Arrays.sort(horizontalCuts);
        Arrays.sort(verticalCuts);

        maxh = Math.max(maxh,horizontalCuts[0]);
        for(int i=1;i<horizontalCuts.length;i++){
            maxh = Math.max(maxh,horizontalCuts[i]-horizontalCuts[i-1]);
        }
        maxh = Math.max(maxh,h-horizontalCuts[horizontalCuts.length-1]);

        maxw = Math.max(maxw,verticalCuts[0]);
        for(int j=1;j<verticalCuts.length;j++){
            maxw = Math.max(maxw,verticalCuts[j]-verticalCuts[j-1]);
        }
        maxw = Math.max(maxw,w-verticalCuts[verticalCuts.length-1]);
        return (int)((maxh * maxw) % 1000000007);
    }
}
