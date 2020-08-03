package tree;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/22
 * @Description:
 *  96. 不同的二叉搜索树
 */
public class NumTrees {

    //动态规划 卡兰特数公式 G(n) = f(1) + f(2) + f(3) + ... + f(n)
    // f(n) = G(i-1) 左子数节点个数 * G(n-i) 右子数节点个数
    // 带入就可以得到 卡特兰数公式
    public int numTrees(int n) {
        int[] G = new int[n + 1];
        G[0] = 1;
        G[1] = 1;

        for (int i = 2; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                G[i] += G[j - 1] * G[i - j];
            }
        }
        return G[n];
    }
}
