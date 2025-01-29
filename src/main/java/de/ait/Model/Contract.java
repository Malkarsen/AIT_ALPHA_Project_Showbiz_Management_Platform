package de.ait.Model;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс Contract представляет собой контракт с артистом.
 * Он содержит информацию о контракте, включая имя артиста, даты начала и окончания,
 * условия и уникальный идентификатор контракта.
 */

public class Contract {

    private String id; // Уникальный идентификатор
    private String artistName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String terms;

    /**
     * Конструктор для создания нового контракта.
     *
     * @param artistName имя артиста.
     * @param startDate  дата начала контракта.
     * @param endDate    дата окончания контракта.
     * @param terms      условия контракта.
     */
    public Contract(String artistName, LocalDate startDate, LocalDate endDate, String terms) {
        if (artistName == null || artistName.isEmpty()) {
            System.out.println("Ошибка: Имя артиста не может быть пустым.");
            return; // Прекращение выполнение конструктора
        }
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            System.err.println("Ошибка: Даты некорректны.");
            return;
        }
        if (terms == null || terms.isEmpty()) {
            System.err.println("Ошибка: Условия не могут быть пустыми.");
            return;
        }

        this.id = UUID.randomUUID().toString(); // Генерация уникального идентификатора
        this.artistName = artistName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.terms = terms;
    }

    /**
     * Получение уникального идентификатора контракта.
     *
     * @return уникальный идентификатор контракта.
     */
    public String getId() {
        return id;
    }

    /**
     * Получение имени артиста.
     *
     * @return имя артиста.
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * Задать имя артиста.
     *
     * @param artistName новое имя артиста.
     */
    public void setArtistName(String artistName) {
        if (artistName == null || artistName.isEmpty()) {
            System.err.println("Ошибка: Имя артиста не может быть пустым.");
            return;
        }
        this.artistName = artistName;
    }

    /**
     * Получение даты начала контракта.
     *
     * @return дата начала контракта.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Задать дату начала контракта.
     *
     * @param startDate новая дата начала контракта.
     */
    public void setStartDate(LocalDate startDate) {
        if (startDate == null || startDate.isAfter(this.endDate)) {
            System.err.println("Ошибка: Дата начала некорректна.");
            return;
        }
        this.startDate = startDate;
    }

    /**
     * Получение даты окончания контракта.
     *
     * @return дата окончания контракта.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Задать дату окончания контракта.
     *
     * @param endDate новая дата окончания контракта.
     */
    public void setEndDate(LocalDate endDate) {
        if (endDate == null || endDate.isBefore(this.startDate)) {
            System.err.println("Ошибка: Дата окончания некорректна.");
            return;
        }
        this.endDate = endDate;
    }

    /**
     * Получение условий контракта.
     *
     * @return условия контракта.
     */
    public String getTerms() {
        return terms;
    }

    /**
     * Описать условия контракта.
     *
     * @param terms новые условия контракта.
     */
    public void setTerms(String terms) {
        if (terms == null || terms.isEmpty()) {
            System.err.println("Ошибка: Условия не могут быть пустыми.");
            return;
        }
        this.terms = terms;
    }

    /**
     * Проверка активности контракта на текущую дату.
     *
     * @return true, если контракт активен, иначе false.
     */
    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return (today.isAfter(startDate) || today.isEqual(startDate)) && (today.isBefore(endDate) || today.isEqual(endDate));
    }

    /**
     * Вычисление количества дней до истечения контракта.
     *
     * @return количество дней до истечения контракта.
     */
    public long daysUntilExpiration() {
        return LocalDate.now().until(endDate).getDays();
    }

    /**
     * Отображение контракта в виде строки.
     *
     * @return строковое представление контракта.
     */
    @Override
    public String toString() {
        return "Contract{" +
                "id='" + id + '\'' +
                ", artistName='" + artistName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", terms='" + terms + '\'' +
                '}';
    }
}
