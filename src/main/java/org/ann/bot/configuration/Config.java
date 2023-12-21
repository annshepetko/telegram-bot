package org.ann.bot.configuration;

import org.ann.bot.code.Bot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class Config {
    @Bean
    public TelegramBotsApi telegramBotsApi(Bot bot) throws TelegramApiException {

        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);
        return api ;
    }

}
