package de.ait.Core;

import de.ait.Model.Contract;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс ContractManager управляет списком контрактов.
 * Предоставляет методы для добавления, отображения и проверки контрактов.
 */

public class ContractManager {

    private List<Contract> contracts; // Коллекция для хранения контрактов

    /**
     * Конструктор для создания нового менеджера контрактов.
     * Инициализирует пустую коллекцию контрактов.
     */
    public ContractManager() {
        this.contracts = new ArrayList<>();
    }

    /**
     * Добавляет новый контракт в список.
     *
     * @param contract контракт, который нужно добавить.
     *                 Если переданный контракт равен null, выводится сообщение об ошибке.
     */
    public void addContract(Contract contract) {
        if (contract == null) {
            System.out.println("Ошибка: Невозможно добавить пустой контракт.");
            return;
        }
        contracts.add(contract);
        System.out.println("Контракт добавлен: " + contract.getId());
    }

    /**
     * Отображает список всех контрактов.
     * Если список пуст, выводится соответствующее сообщение.
     */
    public void displayAllContracts() {
        if (contracts.isEmpty()) {
            System.out.println("Список контрактов пуст.");
            return;
        }
        System.out.println("Список всех контрактов:");
        for (Contract contract : contracts) {
            System.out.println(contract);
        }
    }

    /**
     * Возвращает список всех контрактов.
     *
     * @return список контрактов.
     */
    public List<Contract> getContracts() {
        return contracts;
    }

    /**
     * Проверяет контракты, срок действия которых истекает в ближайшие 30 дней.
     * Выводит список таких контрактов в консоль.
     * Если таких контрактов нет, выводится соответствующее сообщение.
     */
    public void checkExpiringContracts() {
        if (contracts.isEmpty()) {
            System.out.println("Список контрактов пуст. Нечего проверять.");
            return;
        }

        LocalDate today = LocalDate.now();
        LocalDate threshold = today.plusDays(30); // Дата через 30 дней

        System.out.println("Контракты, срок действия которых истекает в ближайшие 30 дней:");
        boolean found = false;

        // Перебираем контракты с помощью обычного цикла
        for (Contract contract : contracts) {
            LocalDate endDate = contract.getEndDate();
            if (!endDate.isBefore(today) && endDate.isBefore(threshold)) {
                System.out.println(contract);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Нет контрактов, срок действия которых истекает в ближайшие 30 дней.");
        }
    }

    /**
     * Уведомляет об истекающих контрактах при запуске программы.
     * Вызывает метод {@link #checkExpiringContracts()} для выполнения проверки.
     */
    public void notifyExpiringContracts() {
        System.out.println("Проверка контрактов на истечение срока действия...");
        checkExpiringContracts();
    }
}
