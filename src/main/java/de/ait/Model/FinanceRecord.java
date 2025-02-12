package de.ait.Model;
/*
Этот класс будет хранить информацию о финансовых записях (доходы/расходы).
 */

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Data // Добавляет геттеры и сеттеры, toString(), equals(), hashCode()
@AllArgsConstructor // создает конструктор со всеми параметрами
@NoArgsConstructor  // создает конструктор без параметров
@Slf4j
@Builder // добавляет Builder для удобного создания объектов
public class FinanceRecord {

    @NonNull
    private int id; // Уникальный идентификатор
    @NonNull
    private RecordType type; // Тип записи: доход/расход
    private double amount; // Сумма
    @NonNull
    private String description; // Описание
    @NonNull
    private LocalDate date; // Дата

    // Проверка на отрицательные значения для amount
    public void setAmount(double amount) {
        if (amount < 0) {
            log.error("Попытка установить отрицательное значение для amount: {}", amount);
            throw new IllegalArgumentException("Сумма не может быть отрицательной");
        }
        this.amount = amount;
        log.debug("Установлено значение amount: {}", amount); // Логирование для отладки
    }

    // Enum для типа записи
    public enum RecordType {
        INCOME,
        EXPENSE
    }
}