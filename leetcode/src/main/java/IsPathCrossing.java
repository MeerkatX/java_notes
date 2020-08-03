import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/28
 * @Description:
 */
public class IsPathCrossing {
    public static void main(String[] args) {
        System.out.println(Integer.MIN_VALUE+Integer.MAX_VALUE);
        System.out.println(new IsPathCrossing().isPathCrossing("NESW"));
    }

    public boolean isPathCrossing(String path) {
        int[] pos = new int[2];
        List<int[]> pt = new ArrayList<>();
        pt.add(new int[]{0,0});
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == 'N') {
                pos[1] += 1;
            }
            if (path.charAt(i) == 'S') {
                pos[1] -= 1;
            }
            if (path.charAt(i) == 'E') {
                pos[0] -= 1;
            }
            if (path.charAt(i) == 'W') {
                pos[0] += 1;
            }
            for (int[] p :pt){
                if (p[0]==pos[0] && p[1]==pos[1]) return true;
            }
            pt.add(new int[]{
                    pos[0],pos[1]
            });
        }
        return false;
    }
}
