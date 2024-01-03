package org.ann.bot.repository;

import org.ann.bot.models.Customer;
import org.ann.bot.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    ArrayList<Ticket> findTicketsByCustomer(Customer customerId);
}