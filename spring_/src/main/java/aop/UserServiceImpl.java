package aop;

import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/28
 * @Description:
 */
@Component
public class UserServiceImpl implements UserService{

    @Override
    public void add() {
        System.out.println("add");
    }

    @Override
    public void remove() {
        System.out.println("remove");
    }
}
