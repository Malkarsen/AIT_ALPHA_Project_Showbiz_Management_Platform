package de.ait.App;

import de.ait.Core.FinanceManager;
import de.ait.Utilities.RecordType;

import java.io.IOException;
import java.time.LocalDate;

public class FinanceManagerApp {

    public static void main(String[] args) {
        FinanceManager manager = new FinanceManager();

        // Добавляем записи
        manager.addRecord(RecordType.INCOME, 1000, "Концерт", LocalDate.now());
        manager.addRecord(RecordType.EXPENSE, 500, "Аренда зала", LocalDate.now());

        // Рассчитываем баланс
        double balance = manager.calculateBalance(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        System.out.println("Баланс: " + balance);

        // Сохраняем записи в файл
        try {
            manager.saveRecordsToFile("finance_records.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Загружаем записи из файла
        try {
            manager.loadRecordsFromFile("finance_records.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
