package com.example.apuntes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApuntesApplication {

	public static void main(String[] args) {
        System.out.println("====== ENV MONGO_URI ======");
        System.out.println(System.getenv("MONGO_URI"));
        System.out.println("===========================");
        System.out.println("====== LOADED MONGO PROPERTY ======");
        System.out.println(System.getProperty("spring.data.mongodb.uri"));


        SpringApplication.run(ApuntesApplication.class, args);
	}
}
