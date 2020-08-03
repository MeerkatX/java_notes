package reference;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/22
 * @Description:
 */
public class MyReference {

    public static MyReference myReference;

    public void isAlive() {
        System.out.println("alive");
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize");
        myReference = this;
    }

    public static void main(String[] args) throws Exception {
        myReference = new MyReference();
        myReference = null;
        System.gc();
        Thread.sleep(500);
        if (myReference != null) {
            myReference.isAlive();
        } else System.out.println("dead");

        myReference = null;
        System.gc();
        Thread.sleep(500);
        if (myReference != null) {
            myReference.isAlive();
        } else System.out.println("dead");
    }
}
