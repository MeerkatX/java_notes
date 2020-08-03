package transaction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/27
 * @Description:
 */
public class Main {


    public static void main(String[] args) {
        //PlatformTransactionManager (DataSourceTransactionManager)
        //
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

    }
}
