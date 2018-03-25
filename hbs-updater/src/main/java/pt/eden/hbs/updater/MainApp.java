package pt.eden.hbs.updater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(MainApp.class, args);

        HelloWorldBean bean = ctx.getBean(HelloWorldBean.class);
        bean.sayHello();
    }
}