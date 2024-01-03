package org.ann.bot.handlers;


import org.ann.bot.Bot;
import org.ann.bot.dto.UserInput;
import org.ann.bot.context.CollectProcessContext;
import org.ann.bot.interfaces.validators.Validator;
import org.springframework.stereotype.Component;

@Component
public class CustomerNameHandler {


    public boolean handle(
            Bot bot,
            UserInput userInput,
            Validator validator,
            CollectProcessContext collectProcessContext
    ) {
        if (validator.validate(userInput.getText()) && userInput.getText() != null){
                collectProcessContext.getCustomer().setName(userInput.getText());
                bot.send(userInput.getChatId(), "Введіть прізвище: ");
                return true;
        }else {
            bot.send(userInput.getChatId(), "Формат введеного ім'я неправильний, повторіть спробу");
            bot.setGettingName(true);
        }
        return false;
    }
}
