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

        // Создание записи через конструктор
        FinanceRecord record = new FinanceRecord(type, amount, description, date);
        financeRecords.add(record);
        log.info("Добавлена новая запись: {}", record);
    }

    /**
     * Метод вычисляет баланс (разницу между доходами и расходами) за указанный период.
     *
     * @param startDate Начальная дата периода
     * @param endDate Конечная дата периода
     * @return Итоговый баланс за период (доходы минус расходы)
     * @throws IllegalArgumentException если начальная дата позже конечной
     */
    public double calculateBalance(LocalDate startDate, LocalDate endDate) {
        // Проверка: начальная дата не должна быть позже конечной
        if (startDate.isAfter(endDate)) {
            log.error("Некорректный диапазон дат: startDate {} идет после endDate {}", startDate, endDate);
            throw new IllegalArgumentException("Начальная дата не может быть позже конечной даты");
        }

        // Вычисление общей суммы доходов за указанный период
        double totalIncome = financeRecords.stream()
                .filter(record -> record.getType() == RecordType.INCOME) // Оставляем только доходы
                .filter(record -> !record.getDate().isBefore(startDate) && !record.getDate().isAfter(endDate)) // Фильтруем по диапазону дат
                .mapToDouble(FinanceRecord::getAmount) // Преобразуем в сумму
                .sum(); // Суммируем

        // Вычисление общей суммы расходов за указанный период
        double totalExpense = financeRecords.stream()
                .filter(record -> record.getType() == RecordType.EXPENSE) // Оставляем только расходы
                .filter(record -> !record.getDate().isBefore(startDate) && !record.getDate().isAfter(endDate)) // Фильтруем по диапазону дат
                .mapToDouble(FinanceRecord::getAmount) // Преобразуем в сумму
                .sum(); // Суммируем

        // Расчет баланса (доходы - расходы)
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
        if (financeRecords.isEmpty()) {
            log.warn("Список финансовых записей пуст. Ничего не будет сохранено.");
            return;
        }

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

        File file = new File(fileName);
        if (!file.exists()) {
            log.error("Файл не найден: {}", fileName);
            throw new FileNotFoundException("Файл не найден: " + fileName);
        }
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

    // Парсинг — это процесс анализа и преобразования данных из одного формата в другой, чтобы их можно было использовать в программе

    private FinanceRecord parseRecordFromString(String line) {
        // проверяем корректность данных.
        String[] parts = line.split(",");   // разделяем строку по запятой
        if (parts.length != 4) { // проверяем, что строка содержит все необходимые части
            log.error("Некорректный формат строки: {}", line);
            throw new IllegalArgumentException("Некорректный формат строки: " + line);
        }

        RecordType type;  // парсим тип записи (RecordType)
        try {
            type = RecordType.valueOf(parts[0]); // преобразуем строку в enum
        } catch (IllegalArgumentException e) {
            log.error("Некорректный тип записи: {}", parts[0]);
            throw new IllegalArgumentException("Некорректный тип записи: " + parts[1]);
        }

        double amount; // парсим сумму
        try {
            amount = Double.parseDouble(parts[1]); // преобразуем строку в число
        } catch (NumberFormatException e) {
            log.error("Некорректная сумма: {}", parts[1]);
            throw new IllegalArgumentException("Некорректная сумма: " + parts[1]);
        }

        String description = parts[2]; // парсим описание
        if (description.trim().isEmpty()) {
            log.error("Описание не может быть пустым");
            throw new IllegalArgumentException("Описание не может быть пустым");
        }

        LocalDate date; // парсим дату
        try {
            date = LocalDate.parse(parts[3]); // преобразуем строку в LocalDate
        } catch (Exception e) {
            log.error("Некорректная дата: {}", parts[3]);
            throw new IllegalArgumentException("Некорректная дата: " + parts[4]);
        }

        return new FinanceRecord(type, amount, description, date);
    }

    /**
     * возвращает список всех финансовых записей.
     *
     * @return Список записей.
     */

    // добавили защиту от изменений списка извне, возвращая копию списка.
    public List<FinanceRecord> getFinanceRecords() {
        return new ArrayList<>(financeRecords); // возвращаем копию списка
    }
}