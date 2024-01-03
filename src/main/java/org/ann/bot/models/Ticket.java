package org.ann.bot.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticket_id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private String date;
}
