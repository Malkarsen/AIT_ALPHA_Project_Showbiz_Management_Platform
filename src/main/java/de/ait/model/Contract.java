package de.ait.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Класс Contract представляет собой контракт с артистом.
 * Он содержит информацию о контракте, включая имя артиста, даты начала и окончания,
 * условия и уникальный идентификатор контракта.
 */

public class Contract {
    private final String id; // Уникальный идентификатор, final так как id не меняется
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
        validateArtistName(artistName);
        validateDates(startDate, endDate);
        validateTerms(terms);

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
        validateArtistName(artistName);
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
        validateDates(startDate, this.endDate);
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
        validateDates(this.startDate, endDate);
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
        validateTerms(terms);
        this.terms = terms;
    }

    ///  *** Методы проверки ***
    private void validateArtistName(String artistName) {
        if (artistName == null || artistName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ошибка: Имя артиста не может быть пустым.");
        }
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Ошибка: Даты не могут быть null.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Ошибка: Дата начала не может быть позже даты окончания.");
        }
    }

    private void validateTerms(String terms) {
        if (terms == null || terms.trim().isEmpty()) {
            throw new IllegalArgumentException("Ошибка: Условия контракта не могут быть пустыми.");
        }
    }

    /**
     * Проверка активности контракта на текущую дату.
     *
     * @return true, если контракт активен, иначе false.
     */
    public boolean isActive() {
        if (startDate == null || endDate == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return !today.isBefore(startDate) && !today.isAfter(endDate);
    }

    /**
     * Вычисление количества дней до истечения контракта.
     *
     * @return количество дней до истечения контракта.
     */

    public long daysUntilExpiration() {
        if (endDate == null) {
            return -1; // Ошибка, если дата не установлена
        }
        LocalDate today = LocalDate.now();
        LocalDate start = this.startDate;

        // Если контракт еще не начался, считаем разницу от startDate, а не от сегодня
        LocalDate referenceDate = today.isBefore(start) ? start : today;

        return ChronoUnit.DAYS.between(referenceDate, endDate);
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