package aop;

import org.aspectj.lang.annotation.After;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Auther: 徐少伟
 * @Date: 2020/5/28
 * @Description:
 */
@EnableAspectJAutoProxy
@Configuration
@ComponentScan("aop")
public class Config {

    @Bean
    public AfterLog afterLog(){
        return new AfterLog();
    }
}
