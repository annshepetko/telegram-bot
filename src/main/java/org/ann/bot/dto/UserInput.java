package org.ann.bot.dto;

import lombok.Data;

@Data
public class UserInput {
    private String text;
    private long chatId;

    public UserInput(String text, long chatId) {
        this.text = text;
        this.chatId = chatId;
    }
}
