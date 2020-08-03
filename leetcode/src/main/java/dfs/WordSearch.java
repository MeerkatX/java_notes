package dfs;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/10
 * @Description:
 */
public class WordSearch {

    public static void main(String[] args) {
        System.out.println(new WordSearch().exist(new char[][]{{'A', 'B', 'C', 'E'}
                , {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "ABCB"));

        System.out.println(new WordSearch().exist(new char[][]{{'a', 'b'}, {'c', 'd'}}, "acdb"));
    }


    private boolean[][] visited;

    public boolean exist(char[][] board, String word) {
        char first = word.charAt(0);
        visited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (first == board[i][j]) {
                    visited[i][j]=true;
                    if (dfs(board, word, 1, i, j)) return true;
                    else visited[i][j]=false;
                }
            }
        }
        return false;
    }

    public boolean check(char c, char[][] board, int x, int y) {
        return (!visited[x][y]) && board[x][y] == c;
    }


    public boolean dfs(char[][] board, String word, int deep, int x, int y) {
        if (deep == word.length()) return true;
        if (x - 1 >= 0 && check(word.charAt(deep), board, x - 1, y)) {
            System.out.println((x - 1) + "," + y);
            visited[x - 1][y] = true;
            if (dfs(board, word, deep + 1, x - 1, y)) return true;
            visited[x - 1][y] = false;
        }

        if (x + 1 < board.length && check(word.charAt(deep), board, x + 1, y)) {
            System.out.println((x + 1) + "," + y);
            visited[x + 1][y] = true;
            if (dfs(board, word, deep + 1, x + 1, y)) return true;
            visited[x + 1][y] = false;
        }

        if (y - 1 >= 0 && check(word.charAt(deep), board, x, y - 1)) {
            System.out.println(x + "," + (y - 1));
            visited[x][y - 1] = true;
            if (dfs(board, word, deep + 1, x, y - 1)) return true;
            visited[x][y - 1] = false;
        }

        if (y + 1 < board[0].length && check(word.charAt(deep), board, x, y + 1)) {
            System.out.println(x + "," + (y + 1));
            visited[x][y + 1] = true;
            if (dfs(board, word, deep + 1, x, y + 1)) return true;
            visited[x][y + 1] = false;
        }
        return false;
    }
}
