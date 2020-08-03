package bs;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/24
 * @Description:
 */
public class ShipWithinDays {

    public static void main(String[] args) {
        System.out.println(new ShipWithinDays().shipWithinDays(new int[]{1,2,3,4,5,6,7,8,9,10},5));
        //new ShipWithinDays().getDay(new int[]{1,2,3,4,5,6,7,8,9,10},15);
    }

    public int shipWithinDays(int[] weights, int D) {
        int total = 0;
        for(int w: weights) total+=w;
        int maxWeight = getMax(weights);
        while(maxWeight < total){
            int middle = maxWeight + (total-maxWeight)/2;
            int d = getDay(weights,middle);
            if(d<=D){
                total = middle;
            }else{
                maxWeight = middle+1;
            }
        }
        return maxWeight;


    }

    public int getMax(int[] weights){
        int max = 0;
        for(int w:weights){
            if(max<w) max = w;
        }
        return max;
    }

    public int getDay(int[] weights, int middle){
        int d = 1;
        int sum = 0;
        for(int i=0;i<weights.length;i++){
            sum += weights[i];
            if(sum > middle){
                sum = weights[i];
                d++;
            }
        }
        return d;
    }
}
