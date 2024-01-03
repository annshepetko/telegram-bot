package org.ann.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;



@Component
public class ButtonInitializer {
    // Метод для ініціалізації конпок на клавіатурі працює наступним чином,
    // повертає об'єкт SendMessage який ми потім відправимо в чат, приймає id чату, список із назв кнопок
    // і як останній параметр приймає індекс кнопки яка буде відправляти контакт, якщо не потрібно мати контакт то передаємо в метод -1
    public SendMessage buttonInitialization(long chatId, List<String> buttonsNames, int contactBtn) {
        List<KeyboardButton> buttons = buttonsInit(buttonsNames);

        if (contactBtn != -1){
            buttons.get(contactBtn).setRequestContact(true);
        }
        KeyboardRow row = new KeyboardRow();

        for (KeyboardButton btn : buttons){
            row.add(btn);
        }

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row); // Додавання рядку до клавіатури

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(keyboard);

        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(markup);
        sendMessage.setText("Оберіть дію:");

        return sendMessage;
    }
    private List<KeyboardButton> buttonsInit(List<String> buttonNames){
        List<KeyboardButton> buttons = new ArrayList<>();
        for (String name : buttonNames){
            KeyboardButton keyboardButton = new KeyboardButton();
            keyboardButton.setText(name);
            buttons.add(keyboardButton);
        }
        return buttons;
    }
}
