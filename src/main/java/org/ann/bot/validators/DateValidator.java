package org.ann.bot.validators;

import org.ann.bot.interfaces.validators.Validator;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component

public class DateValidator implements Validator {

    public boolean validate(String input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        dateFormat.setLenient(false); // Вимагаємо строгу перевірку
        try {
            dateFormat.parse(input);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
