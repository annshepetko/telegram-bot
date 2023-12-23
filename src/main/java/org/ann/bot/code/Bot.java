package org.ann.bot.code;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.ann.bot.code.models.Customer;
import org.ann.bot.code.models.Ticket;
import org.ann.bot.code.repository.CustomerRepository;
import org.ann.bot.code.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
    private  CustomerRepository customerRepository;
    private  TicketRepository ticketRepository;
    private boolean isDataCollecting;
    private final Map<BotState, Boolean> state = new EnumMap<>(BotState.class);
    private enum BotState {
        STARTED,
        GETTING_CUSTOMER_NAME,
        GETTING_CUSTOMER_LASTNAME,
        GETTING_TICKET_DATE,
        GETTING_CUSTOMER_PHONENUMER
    }
    private Customer customer;
    private Ticket ticket;
    public Bot(@Value("6381329177:AAEoyvFOQuZW2WxBex6iqpeCvKpUjzs8W-8") String token) {

    }
    @Autowired
    public Bot(Customer customer, Ticket ticket ,CustomerRepository customerRepository, TicketRepository ticketRepository,@Value("6381329177:AAEoyvFOQuZW2WxBex6iqpeCvKpUjzs8W-8") String token) {
        super(token);
        this.ticketRepository = ticketRepository;
        this.isDataCollecting = false;
        this.customerRepository = customerRepository;
        this.customer = customer;
        this.ticket = ticket;
    }


    private void setState(BotState stage, boolean value) {
        state.put(stage, value);
    }

    private boolean getState(BotState botState) {
        return state.get(botState);
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
    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().hasText() && update.hasMessage()) {
            String messageText = update.getMessage().getText();
            long userId = update.getMessage().getChat().getId();
            long chatId = update.getMessage().getChatId();
            System.out.println(messageText);
            if (messageText.equals("/start") || isDataCollecting) {
                isDataCollecting = true;
                startCollectData(messageText, chatId, userId);
            }

        }
    }


    private void startCollectData(String messageText, long chatId, long userId) {
        if (messageText.equals("/start")) {
            System.out.println(messageText);
            setState(BotState.STARTED, true);
            sendMessage(chatId, "Привіт, це бот для замовлення білету в цирк ! \n Нам знадобляться леякі твої дані ");
            sendMessage(chatId, "Для початку введи своє iм'я: ");
            setState(BotState.GETTING_CUSTOMER_NAME, true);

        } else if (getState(BotState.GETTING_CUSTOMER_NAME)) {
            setState(BotState.GETTING_CUSTOMER_NAME, false);
            customer.setName(messageText);
            System.out.println(messageText);
            sendMessage(chatId, "Тепер введіть своє прізвище: ");
            setState(BotState.GETTING_CUSTOMER_LASTNAME, true);

        } else if (getState(BotState.GETTING_CUSTOMER_LASTNAME)) {
            setState(BotState.GETTING_CUSTOMER_LASTNAME, false);
            System.out.println(messageText);
            customer.setLastname(messageText);
            sendMessage(chatId, "Тепер введіть свій номер телефону: ");
            setState(BotState.GETTING_CUSTOMER_PHONENUMER, true);
        } else if (getState(BotState.GETTING_CUSTOMER_PHONENUMER)) {

            setState(BotState.GETTING_CUSTOMER_PHONENUMER, false);
            this.customer.setPhoneNumber(messageText);
            sendMessage(chatId, "Ввейдіть дату вашого походу в цирк у форматі: dd/mm/yyyy hh:mm. ");
            setState(BotState.GETTING_TICKET_DATE, true);
        } else if (getState(BotState.GETTING_TICKET_DATE)) {
            setState(BotState.GETTING_TICKET_DATE, false);
            sendMessage(chatId, "Це все, зараз надішлемо вам ваш квиток. \n Також відправимо повідомлення про вас оператору ");
            setState(BotState.GETTING_TICKET_DATE, false);
            customer.setTicket_date(messageText);
            customer.setId(userId);

            ticket.setCustomer(this.customer);
            customerRepository.save(this.customer);
            ticketRepository.save(this.ticket);
            isDataCollecting = false;
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
