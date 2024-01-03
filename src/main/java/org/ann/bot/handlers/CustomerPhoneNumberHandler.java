package org.ann.bot.handlers;

import org.ann.bot.Bot;
import org.ann.bot.dto.UserInput;
import org.ann.bot.context.CollectProcessContext;
import org.ann.bot.interfaces.validators.Validator;
import org.springframework.stereotype.Component;

@Component
public class CustomerPhoneNumberHandler {

    public boolean handle(Bot bot, UserInput userInput, Validator validator,CollectProcessContext collectProcessContext ) {
        if (validator.validate(userInput.getText())){
            collectProcessContext.getCustomer().setPhoneNumber(userInput.getText());
            bot.send(userInput.getChatId(), "Введіть дату походу в цирк у форматі dd/mm/yyyy hh:mm : ");
            bot.setGettingPhoneNumber(false);
            bot.setGettingShowDate(true);
            return true;
        }else {
            bot.send(userInput.getChatId(), "Формат введеного номера телефону неправильний, повторіть спробу");
            bot.setGettingPhoneNumber(true);
        }
        return false;
    }
}
