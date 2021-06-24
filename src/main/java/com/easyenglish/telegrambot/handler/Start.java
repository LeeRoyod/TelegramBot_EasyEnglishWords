package com.easyenglish.telegrambot.handler;

import com.easyenglish.telegrambot.State;
import com.easyenglish.telegrammodel.User;
import com.easyenglish.repository.UserJpa;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static com.easyenglish.util.TelegramUtil.createMessageTemplate;

@Component
public class Start implements Handler {
    private static final Logger log = Logger.getLogger(Start.class);
    @Value("${bot.name}")
    private String botUsername;

    private final UserJpa userRepository;

    public Start(UserJpa userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        log.info("Кто-то заинтересовался ботом");
        // Приветствие пользователя
        SendMessage welcomeMessage = createMessageTemplate(user)
                .setText(String.format(
                        "Привет! Я *%s*%nЯ помогу тебе запоминать английские слова", botUsername
                ));
        // Спрашиваем имя
        SendMessage registrationMessage = createMessageTemplate(user)
                .setText("Чтобы начать скажи мне свое имя");
        // Изменение состояния на "ожидание ввода имени"
        user.setBotState(State.ENTER_NAME);
        userRepository.save(user);

        return List.of(welcomeMessage, registrationMessage);
    }

    @Override
    public State operatedBotState() {
        return State.START;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}