package org.ann.bot.validators;

import org.ann.bot.interfaces.validators.Validator;
import org.springframework.stereotype.Component;

@Component
public class NameValidator implements Validator {
    @Override
    public boolean validate(String validatingText) {
        for (char c : validatingText.toCharArray()) {
            if (Character.isDigit(c)) {
                return false; // Якщо знайдено хоча б одну цифру, повертаємо false
            }
        }
        return true; // Якщо немає жодної цифри, повертаємо true
    }
}
