package com.easyenglish;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;


// Аннотация объединяет @Configuration, @EnableAutoConfiguration, @ComponentScan
@SpringBootApplication
public class AppBot {

    public static void main(String[] args) {
        // https://github.com/rubenlagus/TelegramBots/tree/master/telegrambots-spring-boot-starter
        ApiContextInitializer.init();

        SpringApplication.run(AppBot.class, args);
    }
}