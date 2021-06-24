package com.easyenglish.repository;

import com.easyenglish.telegrammodel.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionJpa extends JpaRepository<Question, Integer> {
// SQL выбирает 1 случайный вопрос из таблицы вопросов
    @Query(nativeQuery = true, value = "SELECT *  FROM quiz ORDER BY random() LIMIT 1")
    Question getRandomQuestion();
}