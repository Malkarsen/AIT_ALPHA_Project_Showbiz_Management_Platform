package de.ait.Model;
/*
Этот класс будет хранить информацию о финансовых записях (доходы/расходы).
 */

import java.time.LocalDate;

public class FinanceRecord {
    private int id; // Уникальный идентификатор
    private String type; // Тип записи: доход/расход
    private double amount; // Сумма
    private String description; // Описание
    private LocalDate date; // Дата

    // Конструктор
    public FinanceRecord(int id, String type, double amount, String description, LocalDate date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public String getType() {
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

    @Override
    public String toString() {
        return "FinanceRecord{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
