package com.thd.pos_system_java_final;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PosSystemJavaFinalApplication {
    public static void main(String[] args) {
        SpringApplication.run(PosSystemJavaFinalApplication.class, args);
        System.out.println("Application started. You can access the website at http://localhost:8888");
        System.out.println("Admin account: Username - admin, Password - 123456");
    }

}
