package de.ait.Model;
/*
Этот класс будет хранить информацию о финансовых записях (доходы/расходы).
 */

import de.ait.Utilities.RecordType;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.UUID;

// @Data // Добавляет геттеры и сеттеры, toString(), equals(), hashCode()
// @AllArgsConstructor // создает конструктор со всеми параметрами
// @NoArgsConstructor  // создает конструктор без параметров
@Slf4j
// @Builder // добавляет Builder для удобного создания объектов
@ToString
public class FinanceRecord {

    //@NonNull
    private String id; // Уникальный идентификатор
    //@NonNull
    private RecordType type; // Тип записи: доход/расход
    // @NonNull
    private double amount; // Сумма
    //@NonNull
    private String description; // Описание
    //@NonNull
    private LocalDate date; // Дата

    public FinanceRecord(RecordType type, double amount, String description, LocalDate date) {
        this.id = UUID.randomUUID().toString(); // Генерация уникального ID
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
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

    // Проверка на отрицательные значения для amount
    public void setAmount(double amount) {
        if (amount < 0) {
            log.error("Попытка установить отрицательное значение для amount: {}", amount);
            throw new IllegalArgumentException("Сумма не может быть отрицательной");
        }
        this.amount = amount;
        log.debug("Установлено значение amount: {}", amount); // Логирование для отладки
    }

}