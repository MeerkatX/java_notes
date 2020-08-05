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
//            return;//finally在return前会执行
        }catch (Exception e){
            System.out.println("catch");
        }finally {
            System.out.println("finally");
        }

        int i = 0;
        while(i<3){
            try{
                System.out.println(i++);
            }catch (Exception e){
                System.out.println("catch");
            }finally {
                System.out.println("finally");//每进行一次循环就进入一次finally
            }
        }

    }
}
