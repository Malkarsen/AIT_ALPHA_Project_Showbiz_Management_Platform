package de.ait.core;

import de.ait.model.Casting;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс {@code CastingManager} управляет кастингами ({@link Casting}).
 * Позволяет регистрировать кастинги, искать их по идентификатору и выводить список всех кастингов.
 * <p>
 * Uses SLF4J for logging.
 * </p>
 *
 * The {@code CastingManager} class manages castings ({@link Casting}).
 * It allows registering castings, searching for them by ID, and displaying a list of all castings.
 *
 * @author Anton Cheban
 * @version 1.1
 */
@Slf4j
@Getter
public class CastingManager {
    private Map<String, Casting> castings = new HashMap<>();

    /**
     * Регистрирует новый кастинг.
     * Если переданный кастинг равен {@code null}, регистрация не выполняется.
     *
     * Registers a new casting.
     * If the provided casting is {@code null}, registration is not performed.
     *
     * @param casting объект кастинга для регистрации / the casting object to register
     */
    public void registerCasting(Casting casting) {
        if (casting == null) {
            System.out.println("Casting is null");
            log.error("Casting is null");
        }
        else {
            castings.put(casting.getId(), casting);
            System.out.println("Casting was added: " + casting.getId());
            log.info("Casting was added: " + casting.getId());
        }
    }

    /**
     * Ищет кастинг по его идентификатору.
     * Если идентификатор пустой или равен {@code null}, выводится сообщение об ошибке.
     * Если кастинг найден, он выводится в консоль.
     *
     * Searches for a casting by its ID.
     * If the ID is empty or {@code null}, an error message is printed.
     * If the casting is found, it is printed to the console.
     *
     * @param castingId идентификатор кастинга / the casting ID
     * @return найденный {@link Casting} или {@code null}, если кастинг не найден /
     *         the found {@link Casting} or {@code null} if not found
     */
    public Casting findCasting(String castingId) {
        if (castingId == null || castingId.isEmpty()) {
            System.out.println("Wrong Casting name");
            log.warn("attempt to find Casting with wrong name");
            return null;
        }
        else {
            Casting casting = castings.get(castingId);
            System.out.println(casting);
            log.info("attempt find Casting");

            return casting;
        }
    }

    /**
     * Выводит в консоль список всех зарегистрированных кастингов.
     *
     * Prints a list of all registered castings to the console.
     */
    public void showCastings() {
        for (Map.Entry<String, Casting> entry : castings.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
}
