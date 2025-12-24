package ru.skaldytskiyvladislav.delivery;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.skaldytskiyvladislav.delivery.core.ConfigReader;
import ru.skaldytskiyvladislav.delivery.core.TelegramBot;

@SpringBootApplication
public class Application {



    @Bean
    public ConfigReader configReader() {
        return ConfigReader.getInstance();
    }

    @SneakyThrows
    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);
        TelegramBot bot = context.getBean(TelegramBot.class);
        new TelegramBotsApi(DefaultBotSession.class).registerBot(bot);
    }

}
