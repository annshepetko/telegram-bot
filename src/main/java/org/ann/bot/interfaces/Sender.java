package org.ann.bot.interfaces;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Sender {
    void send(SendMessage sendMessage);

    void setState(boolean state);

}
