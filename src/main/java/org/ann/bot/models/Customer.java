package org.ann.bot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

// всі файли в цій директорії представляються собою моделі в бд

@Data
@Entity

public class Customer {
    @Id
    private Long customer_id;

    private String name;
    private String lastname;
    private  String phoneNumber;
}
