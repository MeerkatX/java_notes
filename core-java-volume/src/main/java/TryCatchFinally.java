/**
 * @Auther: 徐少伟
 * @Date: 2020/7/16
 * @Description:
 */
public class TryCatchFinally {
    public static void main(String[] args) {

        try{
            System.out.println("try");
//            System.exit(0);
            return;
        }catch (Exception e){
            System.out.println("catch");
        }finally {
            System.out.println("finally");
        }

    }
}
