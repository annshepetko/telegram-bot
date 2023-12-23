package org.ann.bot.code.repository;

import org.ann.bot.code.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
