package com.easyenglish.telegrambot.handler;

import com.easyenglish.telegrambot.State;
import com.easyenglish.telegrammodel.User;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

public interface Handler {
// Метод для обработки действия
    List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message);
// Метод для проверки можно ли обработать текущий State у пользователя
    State operatedBotState();
// Метод проверяет какие команды CallBackQuery можно обработать в этом классе
    List<String> operatedCallBackQuery();
}