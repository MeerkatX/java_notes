package ioc.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/2
 * @Description:
 */
@Component
public class Work {

    @Autowired
    public User user;

    public Work(){
        System.out.println("work init");
    }

    @Override
    public String toString() {
        return "work";
    }
}
