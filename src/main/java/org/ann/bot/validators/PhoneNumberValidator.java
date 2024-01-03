package org.ann.bot.validators;

import org.ann.bot.interfaces.validators.Validator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PhoneNumberValidator implements Validator {
    private static final String PHONE_NUMBER_REGEX = "^[0-9]{3}[0-9]{7}$";

    @Override
    public boolean validate(String validatingText) {
            Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
            return pattern.matcher(validatingText).matches();
    }
}
