package de.ait.model;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Класс {@code Casting} представляет собой кастинг с участниками.
 * Позволяет регистрировать участников и обновлять их статус.
 * <p>
 * Uses SLF4J for logging.
 * </p>
 *
 * The {@code Casting} class represents a casting with participants.
 * It allows registering participants and updating their status.
 *
 * @author ВашеИмя / YourName
 * @version 1.0
 */
@Slf4j
@Getter
@ToString
public class Casting {
    private final String id;
    private String name;
    private String description;
    private String location;
    private LocalDate castingDate;
    private Map<String, Participant> participants = new HashMap<>();

    /**
     * Создает новый объект {@code Casting}.
     * <p>
     * Creates a new {@code Casting} object.
     *
     * @param name        название кастинга / name of the casting
     * @param description описание кастинга / description of the casting
     * @param location    местоположение кастинга / location of the casting
     * @param castingDate дата кастинга / casting date
     */
    public Casting(String name, String description, String location, LocalDate castingDate) {

        if (name == null || name.isEmpty()) {
            System.out.println("Incorrect Casting name");
            log.error("Incorrect Casting name");
            throw new IllegalArgumentException("Incorrect Casting name");
        }
        if (description == null || description.isEmpty()) {
            System.out.println("Incorrect Casting description");
            log.error("Incorrect Casting description");
            throw new IllegalArgumentException("Incorrect Casting description");
        }
        if (location == null || location.isEmpty()) {
            System.out.println("Incorrect Casting location");
            log.error("Incorrect Casting location");
            throw new IllegalArgumentException("Incorrect Casting location");
        }
        if (castingDate == null) {
            System.out.println("Incorrect Casting date");
            log.error("Incorrect Casting date");
            throw new IllegalArgumentException("Incorrect Casting date");
        }
        String numericUUID = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        String paddedUUID = String.format("%-16s", numericUUID).replace(' ', '0');
        this.id = paddedUUID.substring(0, 16); // Generation of unique identifier
        this.name = name;
        this.description = description;
        this.location = location;
        this.castingDate = castingDate;
    }
}
