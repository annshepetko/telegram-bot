package org.ann.bot.code.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Component
public class Customer {
    @Id
    private Long id;

    private String name;
    private String lastname;
    private String ticket_date;
    private  String phoneNumber;


}
