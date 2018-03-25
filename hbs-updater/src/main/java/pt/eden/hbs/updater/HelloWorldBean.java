package pt.eden.hbs.updater;

import org.springframework.stereotype.Component;

@Component
public class HelloWorldBean {
    public void sayHello(){
        System.out.println("Hello Spring Boot!");
    }
}