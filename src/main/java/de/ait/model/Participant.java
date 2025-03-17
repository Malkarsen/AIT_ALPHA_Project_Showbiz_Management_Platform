package de.ait.model;

import de.ait.utilities.ParticipantStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Класс Participant представляет участника с уникальным идентификатором, именем и статусом.
 * Использует Lombok для автоматической генерации геттеров и метода toString.
 */
@Slf4j
@Getter
@Setter
@ToString
public class Participant {
    /** Уникальный идентификатор участника */
    private final String id;

    /** Имя участника */
    private String name;

    /** Статус участника */
    private ParticipantStatus status;


    /**
     * Создает новый объект участника.
     *
     * @param name   Имя участника
     * @param status Статус участника
     */


    public Participant(String name, ParticipantStatus status) {

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
        String numericUUID = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        String paddedUUID = String.format("%-16s", numericUUID).replace(' ', '0');
        this.id = paddedUUID.substring(0, 16); // Generation of unique identifier
        this.name = name;
        this.status = status;
    }

}
