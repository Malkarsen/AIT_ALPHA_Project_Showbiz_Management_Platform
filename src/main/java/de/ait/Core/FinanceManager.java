package de.ait.Core;

import de.ait.Model.FinanceRecord;
import de.ait.Utilities.RecordType;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FinanceManager {
    private List<FinanceRecord> financeRecords; // Список финансовых записей

    public FinanceManager() {
        this.financeRecords = new ArrayList<>();
        log.info("FinanceManager инициализирован. Список записей создан.");
    }

    /**
     * Добавляет новую финансовую запись (доход или расход).
     *
     * @param type        Тип записи (INCOME или EXPENSE).
     * @param amount      Сумма.
     * @param description Описание.
     * @param date        Дата.
     */
    public void addRecord(RecordType type, double amount, String description, LocalDate date) {
        FinanceRecord record = new FinanceRecord(type, amount, description, date); // Создание записи через конструктор
        financeRecords.add(record);
        log.info("Добавлена новая запись: {}", record);
    }

    /**
     * Рассчитывает баланс за указанный период.
     *
     * @param startDate Начальная дата.
     * @param endDate   Конечная дата.
     * @return Общий баланс (доходы минус расходы).
     */
    public double calculateBalance(LocalDate startDate, LocalDate endDate) {
        double totalIncome = financeRecords.stream()
                .filter(record -> record.getType() == RecordType.INCOME)
                .filter(record -> !record.getDate().isBefore(startDate) && !record.getDate().isAfter(endDate))
                .mapToDouble(FinanceRecord::getAmount)
                .sum();

        double totalExpense = financeRecords.stream()
                .filter(record -> record.getType() == RecordType.EXPENSE)
                .filter(record -> !record.getDate().isBefore(startDate) && !record.getDate().isAfter(endDate))
                .mapToDouble(FinanceRecord::getAmount)
                .sum();

        double balance = totalIncome - totalExpense;
        log.info("Рассчитан баланс за период с {} по {}: {}", startDate, endDate, balance);
        return balance;
    }

    /**
     * Сохраняет все финансовые записи в файл.
     *
     * @param fileName Имя файла для сохранения.
     * @throws IOException Если произошла ошибка при записи в файл.
     */
    public void saveRecordsToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (FinanceRecord record : financeRecords) {
                writer.write(record.toString());
                writer.newLine();
            }
            log.info("Финансовые записи сохранены в файл: {}", fileName);
        } catch (IOException e) {
            log.error("Ошибка при сохранении записей в файл: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Загружает финансовые записи из файла.
     *
     * @param fileName Имя файла для загрузки.
     * @throws IOException Если произошла ошибка при чтении файла.
     */
    public void loadRecordsFromFile(String fileName) throws IOException {
        financeRecords.clear(); // Очищаем текущие записи перед загрузкой

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Парсинг строки и создание объекта FinanceRecord
                FinanceRecord record = parseRecordFromString(line);
                financeRecords.add(record);
            }
            log.info("Финансовые записи загружены из файла: {}", fileName);
        } catch (IOException e) {
            log.error("Ошибка при загрузке записей из файла: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Парсит строку и создает объект FinanceRecord.
     * (Этот метод нужно реализовать в зависимости от формата записи в файле)
     *
     * @param line Строка из файла.
     * @return Объект FinanceRecord.
     */
    private FinanceRecord parseRecordFromString(String line) {
        String[] parts = line.split(",");
        String id = parts[0];
        RecordType type;
        try {
            type = RecordType.valueOf(parts[1]); // Пытаемся преобразовать строку в enum
        } catch (IllegalArgumentException e) {
            log.error("Некорректный тип записи: {}", parts[1]);
            throw new IllegalArgumentException("Некорректный тип записи: " + parts[1]);
        }
        double amount = Double.parseDouble(parts[2]);
        String description = parts[3];
        LocalDate date = LocalDate.parse(parts[4]);

        return new FinanceRecord(type, amount, description, date);
    }

    /**
     * Возвращает список всех финансовых записей.
     *
     * @return Список записей.
     */
    public List<FinanceRecord> getFinanceRecords() {
        return financeRecords;
    }
}