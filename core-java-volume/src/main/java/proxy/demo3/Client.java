package proxy.demo3;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/28
 * @Description: 动态代理
 */
public class Client {
    public static void main(String[] args) {
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");//ProxyGenerator
        Host host = new Host();
        ProxyInvocationHandler pih = new ProxyInvocationHandler();
        pih.setRent(host);
        Rent proxy = (Rent) pih.getProxy();
        proxy.rent();
        System.out.println(proxy.getClass());
    }
}
