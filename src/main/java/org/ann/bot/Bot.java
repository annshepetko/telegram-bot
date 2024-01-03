package org.ann.bot;

import lombok.Data;
import org.ann.bot.comands.HelpCommand;
import org.ann.bot.dto.UserInput;
import org.ann.bot.repository.CustomerRepository;
import org.ann.bot.repository.TicketRepository;
import org.ann.bot.comands.StartCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;



@Component
@Data
public class Bot extends TelegramLongPollingBot  {

    public static final String HELP = "/help";
    public static final String START = "/start";

    @Autowired
    private StartCommand startCommand;
    @Autowired
    private HelpCommand helpCommand;

    private boolean isDataCollecting = false;  // відповідає за загальний процес збору даних

    private boolean isGettingName = false;
    private boolean isGettingLastName = false;
    private boolean isGettingPhoneNumber = false;
    private boolean isGettingShowDate = false;
    // збір даних працює за аналогією ланцюжка, наприклад коли ми чекаємо ім'я - користувач має ввести ім'я, і тд..
    // цей процес ми регудюємо за лопомогою цих ж перемикачів що вгорі

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            if (update.getMessage().hasText()) {
                UserInput userInput = new UserInput(update.getMessage().getText(), update.getMessage().getChatId());
                if (userInput.getText().equals(START)){
                    startCommand.helloMessage(userInput, this);
                }else if (userInput.getText().equals(HELP)){
                    helpCommand.helpMessage(this, userInput);
                }
                else {
                    if (userInput.getText().equals("Вручну") || isDataCollecting) {
                        this.isDataCollecting = true;
                        startCommand.manuallyRegister(userInput, this);

                    }else if (this.isGettingLastName){
                        startCommand.setCustomerLastname(userInput, this);
                    }else if (this.isGettingShowDate){
                        startCommand.setTicketDate(userInput, this);
                    }
                    else if (!isDataCollecting){
                        startCommand.generateTicketResponse(this, userInput, update.getMessage().getChat().getUserName());
                    }
                }
            }else if (update.getMessage().hasContact()){
                 startCommand.contactRegister(update.getMessage().getContact(), this);
            }
        }
    }
    @Autowired
    public Bot(@Value("${bot.token}") String token, CustomerRepository customerRepository, TicketRepository ticketRepository) {
        super(token);
    }

    public void send(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "circus_bot";
    }

    @Override
    public  String getBotToken(){
        return "6381329177:AAEoyvFOQuZW2WxBex6iqpeCvKpUjzs8W-8";
    }
}
