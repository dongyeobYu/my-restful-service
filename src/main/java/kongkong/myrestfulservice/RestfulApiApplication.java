package kongkong.myrestfulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RestfulApiApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(RestfulApiApplication.class, args);
    }

}
