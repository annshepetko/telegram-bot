package org.ann.bot.comands;

import org.ann.bot.Bot;
import org.ann.bot.dto.UserInput;
import org.springframework.stereotype.Component;

@Component

public class HelpCommand {
    public void helpMessage(Bot bot, UserInput userInput){
        bot.send(userInput.getChatId(), "Це бот для замовлення квитків у цирк," +
                " скористуйтесь командою " + Bot.START + " для початку роботи, бот збирає дані які ви вводите та формулює вам квиток, ось такий не складний бот ");
    }
}
