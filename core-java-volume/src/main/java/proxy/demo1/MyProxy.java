package proxy.demo1;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/28
 * @Description:
 */
public class MyProxy {

    private Rent host;

    public MyProxy() {
    }

    public MyProxy(Rent host) {
        this.host = host;
    }

    public void rent() {
        this.host.rent();
        seeHouse();
        fare();
    }

    public void seeHouse() {
        System.out.println("看");
    }

    public void fare() {
        System.out.println("收费");
    }
}
