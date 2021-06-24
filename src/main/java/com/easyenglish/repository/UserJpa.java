package com.easyenglish.repository;

import com.easyenglish.telegrammodel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserJpa extends JpaRepository<User, Integer> {
	// Получение пользователя по chatId
    Optional<User> getByChatId(int chatId);
}