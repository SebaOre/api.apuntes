package com.example.apuntes.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String dbName;

    @Bean
    public MongoClient mongoClient() {
        System.out.println(">>> USANDO MONGO URI:");
        System.out.println(mongoUri);
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        System.out.println(">>> USANDO DATABASE:");
        System.out.println(dbName);
        return new MongoTemplate(mongoClient(), dbName);
    }
}
