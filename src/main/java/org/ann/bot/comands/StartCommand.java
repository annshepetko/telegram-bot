package org.ann.bot.comands;

import lombok.RequiredArgsConstructor;
import org.ann.bot.Bot;
import org.ann.bot.ButtonInitializer;
import org.ann.bot.context.CollectProcessContext;
import org.ann.bot.dto.UserInput;
import org.ann.bot.models.Customer;
import org.ann.bot.validators.DateValidator;
import org.ann.bot.validators.LastnameValidator;
import org.ann.bot.validators.NameValidator;
import org.ann.bot.validators.PhoneNumberValidator;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Contact;

import java.util.List;
@Component
@RequiredArgsConstructor
public class StartCommand {
    private final ButtonInitializer buttonInitializer;
    private final CollectProcessContext collectProcessContext;
    private final DateValidator dateValidator;
    private final LastnameValidator lastnameValidator;
    private final NameValidator nameValidator;
    private final PhoneNumberValidator phoneNumberValidator;

    // метод представляє обою збір даних вручну через задавання питань користувачу
    public void manuallyRegister(UserInput userInput, Bot bot){
        if ( userInput.getText().equals("Вручну")){
            bot.send(userInput.getChatId(), "Введіть ім'я: ");
            bot.setGettingName(true);
        }else if(bot.isGettingName()){
            boolean isNameValid = this.collectProcessContext.getCustomerNameHandler().handle(
                    bot,
                    userInput,
                    nameValidator,
                    this.collectProcessContext
            );
            if (isNameValid) {
                bot.setGettingName(false);
                bot.setGettingLastName(true);
            }
        }else if(bot.isGettingLastName()){
          boolean isLastnameValid = collectProcessContext.getCustomerLastNameHandler().handle(
                    bot,
                    userInput,
                    lastnameValidator,
                    this.collectProcessContext
            );
          if (isLastnameValid){
              bot.setGettingLastName(false);
              bot.setGettingPhoneNumber(true);
          }

        }else if(bot.isGettingPhoneNumber()){

           boolean isPhoneNumberValid = collectProcessContext.getCustomerPhoneNumberHandler().handle(
                    bot,
                    userInput,
                    phoneNumberValidator,
                    this.collectProcessContext
            );
           if (isPhoneNumberValid){
               bot.setGettingPhoneNumber(false);
               bot.setGettingShowDate(true);
           }
        }else if(bot.isGettingShowDate()){

          boolean isDateValid =  collectProcessContext.getGettingTicketDate().handle(
                    bot,
                    userInput,
                    dateValidator,
                    this.collectProcessContext
            );
          if (isDateValid){
              bot.setGettingShowDate(false);
              bot.setDataCollecting(false);
          }
        }
    }

    public void helloMessage(UserInput userInput ,Bot bot){

        bot.send(userInput.getChatId(), "Привіт, це бот для замовлення квитка в цирк, нам потрібні деякі ваші дані, оберіть варіант збору даних ");
        bot.send(buttonInitializer.buttonInitialization(userInput.getChatId(), List.of("Надіслати контакт", "Вручну"), 0));
    }

    // метод збирає дані про користувача через апрошення ого контакту
    public void contactRegister(Contact contact, Bot bot){
        Customer customer = collectProcessContext.getCustomer();

        customer.setName(contact.getFirstName());
        customer.setLastname(contact.getLastName());
        customer.setPhoneNumber(contact.getPhoneNumber());

        if (customer.getLastname() == null){ // перевіряє чи є в контакті прізвище
            // (багато користувачів не вказують прізвище і саме через це прийдеться запросити дані про прізвище вручну )
            bot.send(contact.getUserId(), "Введіть прізвище: ");
            bot.setGettingLastName(true);
        }
        bot.setDataCollecting(false);
    }
    public void  setCustomerLastname(UserInput userInput, Bot bot){
        if (this.lastnameValidator.validate(userInput.getText())){

            this.collectProcessContext.getCustomer().setLastname(userInput.getText());
            bot.send(userInput.getChatId(), "Введіть дату походу в цирк у форматі dd/MM/yyyy hh:mm");
            bot.setGettingLastName(false);
            bot.setGettingShowDate(true);
        } else {
            bot.send(userInput.getChatId(),"Формат введеного прізвища неправильний, спробуйте ще раз");
        }
    }
    public void setTicketDate(UserInput userInput, Bot bot){

     boolean isDateValid = this.collectProcessContext.getGettingTicketDate().handle(bot, userInput, this.dateValidator, this.collectProcessContext);
        if (isDateValid){
            bot.setGettingShowDate(false);
        }
    }
    public void generateTicketResponse(Bot bot, UserInput userInput, String telegramNickname){
        this.collectProcessContext.generateTicketResponse(bot, userInput, telegramNickname);
    }

}
