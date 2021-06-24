// Класс, описывающий любой объект, у которого может быть id

package com.easyenglish.telegrammodel;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// Суперкласс для всех объектов
@MappedSuperclass
@Access(AccessType.FIELD)
// Lombok для автогенерации сеттеров и геттеров
@Getter
@Setter
public abstract class AbstractBaseEntity {
    public static final int START_SEQ = 100000;

    // Аннотации для генерации id
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")

    protected Integer id;

    protected AbstractBaseEntity() {
    }
}