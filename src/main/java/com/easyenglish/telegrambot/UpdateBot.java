package com.easyenglish.telegrambot;

import com.easyenglish.telegrambot.handler.Handler;
import com.easyenglish.telegrammodel.User;
import com.easyenglish.repository.UserJpa;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
public class UpdateBot {
    // Хранение доступных handlers в списке
    private final List<Handler> handlers;
    // Доступ в базу пользователей
    private final UserJpa userRepository;

    public UpdateBot(List<Handler> handlers, UserJpa userRepository) {
        this.handlers = handlers;
        this.userRepository = userRepository;
    }

    // Обработка Update
    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {
        // При несуществующей команде возвращать пустой список
        try {
            // Если Update - сообщение с текстом
            if (isMessageWithText(update)) {
                // Сообщение из Update
                final Message message = update.getMessage();
                // ID чата с пользователем
                final int chatId = message.getFrom().getId();

                // Проверяем пользователя. Если пользователя нет - создаем нового и возвращаем его
                final User user = userRepository.getByChatId(chatId)
                        .orElseGet(() -> userRepository.save(new User(chatId)));
                // Поиск нужного обработчика и возврат его результата
                return getHandlerByState(user.getBotState()).handle(user, message.getText());
            // для CallBackQuery
            } else if (update.hasCallbackQuery()) {
                final CallbackQuery callbackQuery = update.getCallbackQuery();
                final int chatId = callbackQuery.getFrom().getId();
                final User user = userRepository.getByChatId(chatId)
                        .orElseGet(() -> userRepository.save(new User(chatId)));

                return getHandlerByCallBackQuery(callbackQuery.getData()).handle(user, callbackQuery.getData());
            }

            throw new UnsupportedOperationException();
        } catch (UnsupportedOperationException e) {
            return Collections.emptyList();
        }
    }

    private Handler getHandlerByState(State state) {
        return handlers.stream()
                .filter(h -> h.operatedBotState() != null)
                .filter(h -> h.operatedBotState().equals(state))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private Handler getHandlerByCallBackQuery(String query) {
        return handlers.stream()
                .filter(h -> h.operatedCallBackQuery().stream()
                        .anyMatch(query::startsWith))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }
}