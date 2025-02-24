package de.ait.model;

import de.ait.utilities.RecordType;
//import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Этот класс хранит информацию о финансовых записях (доходы/расходы).
 */
@Slf4j
public class FinanceRecord {

    private final String id; // Уникальный идентификатор. Генерируется автоматически.
    //@NonNull
    private final RecordType type; // Тип записи: доход/расход
    private double amount; // Сумма. Должна быть положительной.
    //@NonNull
    private final String description; // Описание. Не может быть пустым.
    //@NonNull
    private final LocalDate date; // Дата. Не может быть в будущем.

    /**
     * Конструктор для создания финансовой записи.
     *
     * @param type        Тип записи (INCOME или EXPENSE). Не может быть null.
     * @param amount      Сумма. Должна быть больше 0.
     * @param description Описание. Не может быть null или пустым.
     * @param date        Дата. Не может быть null или в будущем.
     * @throws IllegalArgumentException Если данные некорректны.
     * @throws NullPointerException     Если переданы null-значения.
     */
    public FinanceRecord(RecordType type, double amount, String description, LocalDate date) {
        // Проверка типа записи (enum)
        if (type == null) {
            log.error("Ошибка: RecordType передан как null");
            throw new IllegalArgumentException("RecordType не может быть null");
        }

        // Проверка суммы (должна быть > 0)
        if (amount <= 0) {
            log.error("Ошибка: Некорректная сумма (amount={}): сумма должна быть больше 0", amount);
            throw new IllegalArgumentException("Сумма должна быть больше 0");
        }

        // Проверка описания (не должно быть пустым или содержать только пробелы)
        if (description == null || description.trim().isEmpty()) {
            log.error("Ошибка: Пустое или null описание");
            throw new IllegalArgumentException("Описание не может быть пустым");
        }

        // Проверка даты (не должна быть в будущем)
        if (date == null || date.isAfter(LocalDate.now())) {
            log.error("Ошибка: Неверная дата (date={}): дата не может быть в будущем", date);
            throw new IllegalArgumentException("Дата не может быть в будущем");
        }

        this.id = UUID.randomUUID().toString(); // Генерация уникального ID
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;

        log.debug("Создана новая финансовая запись: {}", this);
    }

    public String getId() {
        return id;
    }

    public RecordType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Устанавливает новую сумму для записи.
     *
     * @param amount Новая сумма. Должна быть больше 0.
     * @throws IllegalArgumentException Если сумма некорректна.
     */
    public void setAmount(double amount) {
        if (amount <= 0) { // Проверка на положительное значение
            log.error("Попытка установить некорректное значение для amount: {}", amount);
            throw new IllegalArgumentException("Сумма должна быть больше 0");
        }
        this.amount = amount;
        log.debug("Установлено значение amount: {}", amount);
    }

    @Override
    public String toString() {
        return "FinanceRecord{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
        +
                '}';
    }
}