package proxy.demo1;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/28
 * @Description: 静态代理
 */
public class Client {

    public static void main(String[] args) {
        Host host = new Host();
//        host.rent();
        MyProxy myProxy = new MyProxy(host);
        myProxy.rent();
    }
}
