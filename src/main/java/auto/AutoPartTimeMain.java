package auto;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author taoxinglong
 * @description TODO
 * @date 2024-08-29 14:31
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("auto.dao")
public class AutoPartTimeMain {
    public static void main(String[] args) {
        SpringApplication.run(AutoPartTimeMain.class, args);
    }
}
