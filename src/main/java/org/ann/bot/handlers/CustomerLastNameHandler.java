package org.ann.bot.handlers;

import org.ann.bot.Bot;
import org.ann.bot.dto.UserInput;
import org.ann.bot.context.CollectProcessContext;
import org.ann.bot.interfaces.validators.Validator;
import org.springframework.stereotype.Component;

// Всі файли в цій дирекорії представляють собою обробники повідомлень користувача
// які приймають дані від нього та заносять в сутності квитка та клієнта, тим самим будуючи моделі і в останньому обробнику зберігають дані в бд
@Component
public class CustomerLastNameHandler {

    public boolean handle(Bot bot, UserInput userInput, Validator validator, CollectProcessContext collectProcessContext) {
        if (validator.validate(userInput.getText()) && userInput.getText() != null){
            collectProcessContext.getCustomer().setLastname(userInput.getText());
            bot.send(userInput.getChatId(), "Введіть номер телефону у форматі XXXYYYYYYY  \n " +
                    "наприклад 0684561321: ");
            return true;
        }else {
            bot.send(userInput.getChatId(), "Формат введеного прізвища неправильний, повторіть спробу");
            bot.setGettingLastName(true);
        }
        return false;
    }
}
