package org.ann.bot.context;

import lombok.Data;
import org.ann.bot.Bot;
import org.ann.bot.dto.UserInput;
import org.ann.bot.handlers.CustomerLastNameHandler;
import org.ann.bot.handlers.CustomerNameHandler;
import org.ann.bot.handlers.CustomerPhoneNumberHandler;
import org.ann.bot.handlers.GettingTicketDate;
import org.ann.bot.models.Customer;
import org.ann.bot.models.Ticket;
import org.ann.bot.repository.CustomerRepository;
import org.ann.bot.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


// зберігає в соі всю інформацію про репозиторії, обробники повідомлень та сутності квитка і клієнта
@Data
@Component
public  class CollectProcessContext {
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    private TicketRepository ticketRepository;

    private CustomerNameHandler customerNameHandler;
    private CustomerLastNameHandler customerLastNameHandler;
    private CustomerPhoneNumberHandler customerPhoneNumberHandler;
    private GettingTicketDate gettingTicketDate;
    @Autowired
    public CollectProcessContext(
            CustomerNameHandler customerName,
            CustomerLastNameHandler customerLastname,
            CustomerPhoneNumberHandler customerPhoneNumber,
            GettingTicketDate gettingTicketDate
    ){
        this.customerNameHandler = customerName;
        this.customerLastNameHandler = customerLastname;
        this.customerPhoneNumberHandler = customerPhoneNumber;
        this.gettingTicketDate = gettingTicketDate;
    }

    private Customer customer = new Customer();
    private Ticket ticket = new Ticket();

public void generateTicketResponse(Bot bot, UserInput userInput, String telegramNickname){
    String responseMessage = "Квиток: " +
            "\n Унікальний номер квитка: " + this.ticket.getTicket_id() +
            "\n Ім'я: " + this.customer.getName() +
            "\n Прізвище: " + this.customer.getLastname() +
            "\n Номер телефону: " + this.customer.getPhoneNumber() +
            "\n Дата походу в цирк: " + this.ticket.getDate();
    bot.send(userInput.getChatId(), responseMessage);

    bot.send(847222227, responseMessage + "\n Нікнейм телеграму: " + telegramNickname );

    this.ticket = new Ticket();
    this.customer = new Customer();

}

}
