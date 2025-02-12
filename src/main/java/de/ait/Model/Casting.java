package de.ait.Model;

import de.ait.Utilities.ParticipantStatus;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
    private String id;
    private String name;
    private String description;
    private String location;
    private LocalDate castingDate;
    private Map<String, Participant> participants = new HashMap<>();

    /**
     * Создает новый объект {@code Casting}.
     *
     * Creates a new {@code Casting} object.
     *
     * @param id уникальный идентификатор кастинга / unique ID of the casting
     * @param name название кастинга / name of the casting
     * @param description описание кастинга / description of the casting
     * @param location местоположение кастинга / location of the casting
     */
    public Casting(String id, String name, String description, String location, LocalDate castingDate) {
        if (id == null || id.isEmpty()) {
            System.out.println("Incorrect Casting id");
            log.error("Incorrect Casting id");
            throw new IllegalArgumentException("Incorrect Casting id");
        }
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
            System.out.println("Incorrect Casting location");
            log.error("Incorrect Casting location");
            throw new IllegalArgumentException("Incorrect Casting location");
        }
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.castingDate = castingDate;
    }

    /**
     * Регистрирует нового участника в кастинге.
     * Если переданный участник {@code null}, регистрация не выполняется.
     *
     * Registers a new participant in the casting.
     * If the provided participant is {@code null}, registration is not performed.
     *
     * @param participant участник для регистрации / the participant to register
     */
    public void registerParticipant(Participant participant) {
        if (participant == null) {
            System.out.println("Participant is null");
            log.warn("attempt to registered null");
        }
        else {
            this.participants.put(participant.getId(), participant);
            System.out.println("New participant was added");
            log.info("new participant was added");
        }
    }

    /**
     * Обновляет статус участника.
     * Если идентификатор участника или новый статус равны {@code null} или пусты, обновление не выполняется.
     *
     * Updates the status of a participant.
     * If the participant ID or new status is {@code null} or empty, the update is not performed.
     *
     * @param participantId идентификатор участника / the participant's ID
     * @param newStatus новый статус участника / the new status of the participant
     * @return {@code true}, если статус был успешно обновлен; {@code false}, если участник не найден или данные некорректны /
     *         {@code true} if the status was successfully updated; {@code false} if the participant was not found or data was incorrect
     */
    public boolean updateParticipantStatus(String participantId, ParticipantStatus newStatus) {
        if (participantId == null || participantId.isEmpty() || newStatus == null) {
            System.out.println("Participant or new status is null");
            log.error("Status update is not possible. Participant: {}, Status: {}", participantId, newStatus);
            return false;
        } else {
            if (participants.containsKey(participantId)) {
                Participant participant = this.participants.get(participantId);
                participant.setStatus(newStatus);
                log.info("Status of Participant {} was updated to {}", participantId, newStatus);
                return true;
            } else {
                System.out.println("This participant has not yet registered");
                log.warn("attempt update states for not registered participant");
                return false;
            }
        }
    }

    /**
     * Выводит всех зарегистрированных участников кастинга.
     *
     * Prints all registered participants of the casting.
     */
    public void showParticipants() {
        new HashMap<>(participants);

        for (Participant participant : participants.values()) {
            System.out.println(participant);
        }
    }
}
