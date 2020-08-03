package proxy.demo3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/28
 * @Description:
 */
public class ProxyInvocationHandler implements InvocationHandler {

    private Rent rent;

    public Object getProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), rent.getClass().getInterfaces(), this);
    }

    //处理代理实例，并返回结果
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //动态代理的本质就是使用反射机制
        log(method.getName());
        Object result = method.invoke(rent, args);
        return result;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    public void log(String msg) {
        System.out.println(msg);
    }
}
