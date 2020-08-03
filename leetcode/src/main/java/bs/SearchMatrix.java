package bs;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/30
 * @Description:
 * 74. 搜索二维矩阵
 */
public class SearchMatrix {

    public static void main(String[] args) {
        System.out.println(new SearchMatrix().searchMatrix(new int[][]{{1,3,5,7},{10,11,16,20},{23,30,34,50}},3));
    }
    public boolean searchMatrix(int[][] matrix, int target) {
        int H = matrix.length;
        int W = matrix[0].length;
        int n = W * H;


        int left = 0;
        int right = n;

        while(left < right){
            int mid = left + (right-left)/2;
            int midvalue = matrix[mid/W][mid%W];
            if(target > midvalue){
                left = mid+1;
            }else if(target < midvalue){
                right = mid;
            }else{
                return true;
            }
        }
        return false;
    }

}
