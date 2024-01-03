package org.ann.bot.handlers;

import org.ann.bot.Bot;
import org.ann.bot.ButtonInitializer;
import org.ann.bot.dto.UserInput;
import org.ann.bot.context.CollectProcessContext;
import org.ann.bot.interfaces.validators.Validator;
import org.ann.bot.models.Customer;
import org.ann.bot.models.Ticket;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GettingTicketDate {
    ButtonInitializer buttonInitializer = new ButtonInitializer();

    public boolean handle(Bot bot, UserInput userInput, Validator validator, CollectProcessContext context) {
        Customer customer = context.getCustomer();
        Ticket ticket = context.getTicket();
        if (validator.validate(userInput.getText())){


            customer.setCustomer_id(userInput.getChatId());
            ticket.setDate(userInput.getText());
            ticket.setCustomer(customer);

            context.getCustomerRepository().save(customer);
            context.getTicketRepository().save(ticket);

            List<Ticket> listTickets = context.getTicketRepository().findTicketsByCustomer(customer);
            ticket.setTicket_id(listTickets.get(listTickets.size() -1).getTicket_id()); // зроблено для можливості замовити багато квитків в одному чаті не перезапускаючи сервер

            bot.send(buttonInitializer.buttonInitialization(userInput.getChatId(), List.of("Підтвердити"), -1));
            return true;

        }else {
            bot.send(userInput.getChatId(), "Формат введеної дати неправильний, повторіть спробу");
        }
        return false;
    }

}
