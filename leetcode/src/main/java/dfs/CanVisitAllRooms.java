package dfs;

import java.util.List;

/**
 * @ClassName: CanVisitAllRooms
 * @Auther: MeerkatX
 * @Date: 2020-08-31 16:14
 * @Description: 841. 钥匙和房间 图，深度优先遍历
 */
public class CanVisitAllRooms {


    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int[] visited = new int[rooms.size()];
        dfs(visited,rooms,0);
        for (int i:visited){
            if (i != 1) return false;
        }
        return true;
    }

    public void dfs(int[] visited, List<List<Integer>> rooms, int room) {
        if (visited[room] == 1) return;
        else visited[room] = 1;
        List<Integer> keys = rooms.get(room);
        for (int i = 0; i< keys.size(); i++){
            if (visited[keys.get(i)] == 0){
                dfs(visited,rooms,keys.get(i));
            }else{
                continue;
            }
        }
    }

}
