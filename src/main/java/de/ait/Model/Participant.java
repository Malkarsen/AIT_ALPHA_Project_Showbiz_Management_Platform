package de.ait.Model;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс Participant представляет участника с уникальным идентификатором, именем и статусом.
 * Использует Lombok для автоматической генерации геттеров и метода toString.
 */
@Slf4j
@Getter
@ToString
public class Participant {
    /** Уникальный идентификатор участника */
    private String id;

    /** Имя участника */
    private String name;

    /** Статус участника */
    private ParticipantStatus status;

    /**
     * Создает новый объект участника.
     *
     * @param id     Уникальный идентификатор участника
     * @param name   Имя участника
     * @param status Статус участника
     */
    public Participant(String id, String name, ParticipantStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    /**
     * Устанавливает новый идентификатор участника.
     *
     * @param id Новый идентификатор
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Устанавливает новое имя участника.
     *
     * @param name Новое имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Устанавливает новый статус участника.
     *
     * @param status Новый статус
     */
    public void setStatus(ParticipantStatus status) {
        this.status = status;
    }
}
