package ru.biblio.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootBiblioApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootBiblioApplication.class);
    }
}
