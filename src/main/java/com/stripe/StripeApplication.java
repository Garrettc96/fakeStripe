package com.stripe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
@EnableConfigurationProperties(DatabaseConfig.class)
@EnableJpaRepositories("com.stripe.*")
@ComponentScan(basePackages = { "com.stripe.*"})
@EntityScan("com.stripe.*")
@SpringBootApplication
public class StripeApplication {
    public static void main(String[] args) {
        SpringApplication.run(StripeApplication.class, args);
    }
}
