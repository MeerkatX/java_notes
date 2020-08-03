package transaction;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Auther: 徐少伟
 * @Date: 2020/6/27
 * @Description:
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("transaction")
public class Config {
}
