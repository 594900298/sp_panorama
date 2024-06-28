import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "com.example")
@MapperScan("com.example.*.mapper")
public class SpAdminApplication{
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC+8"));
        SpringApplication.run(SpAdminApplication.class, args);
    }
}
