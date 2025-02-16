package de.ait.Model;

import de.ait.Utilities.ParticipantStatus;
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
        if (id == null || id.isEmpty()) {
            System.out.println("Incorrect participant id");
            log.error("Incorrect participant id");
            throw new IllegalArgumentException("Incorrect participant id");
        }
        if (name == null || name.isEmpty()) {
            System.out.println("Incorrect participant name");
            log.error("Incorrect participant name");
            throw new IllegalArgumentException("Incorrect participant name");
        }
        if (status == null) {
            System.out.println("Incorrect participant id");
            log.error("Incorrect participant id");
            throw new IllegalArgumentException("Incorrect participant id");
        }
        this.id = id;
        this.name = name;
        this.status = status;
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
