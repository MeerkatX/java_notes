package twopoint;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/1
 * @Description:
 */
public class CompareVersion {
    public static void main(String[] args) {
        new CompareVersion().compareVersion("0.1","1.1");
    }

    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        int i = 0, j = 0;
        while(i<v1.length || j<v2.length){
            int v1n = i>=v1.length?0:Integer.valueOf(v1[i++]);
            int v2n = j>=v2.length?0:Integer.valueOf(v2[j++]);
            if(v1n > v2n) return 1;
            else if(v1n< v2n) return -1;
        }
        return 0;
    }


}
