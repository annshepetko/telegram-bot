package org.ann.bot.code;

import org.ann.bot.code.models.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.EnumMap;
import java.util.Map;

public class StartStage extends TelegramLongPollingBot {
    private String token;
    public StartStage(@Value("${bot.token}") String token) {
        this.token = token;
    }

    private static final Map<BotState, Boolean> state = new EnumMap<>(BotState.class);

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "circus_bot";
    }




    public void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();

        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
