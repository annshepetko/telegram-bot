package org.ann.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Головний клас для запусуку проекту
@SpringBootApplication
public class BotApplication {
    public static void main(String args[]){
        SpringApplication.run(BotApplication.class, args);
    }
}