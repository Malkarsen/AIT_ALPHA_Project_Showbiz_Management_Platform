package de.ait.Core;


/*
Этот класс будет управлять финансовыми записями и реализовывать основную логику.
 */


import de.ait.Model.FinanceRecord;

public class FinanceManager {
    private List<FinanceRecord> records; // Список финансовых записей
    private int nextId = 1; // Счетчик для уникальных идентификаторов

    // Конструктор
    public FinanceManager() {
        records = new ArrayList<>();
        loadData(); // Загрузка данных при старте
    }
}