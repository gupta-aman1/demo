package com.example.demo;

import com.example.demo.service.StudentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private final ApplicationContext context;

    public DemoApplication(ApplicationContext context){
        this.context = context;
    }

    @Override
    public void run(String... args) {

        StudentService service =
                context.getBean(StudentService.class);

        System.out.println(service);
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
