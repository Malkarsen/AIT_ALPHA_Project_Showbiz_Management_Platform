package de.ait.core;

import de.ait.model.Contract;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * Класс ContractManager управляет списком контрактов.
 * Предоставляет методы для добавления, отображения и проверки контрактов.
 */
public class ContractManager {
    private final List<Contract> contracts; // // Используем final, так как коллекция не будет меняться

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
            throw new IllegalArgumentException("Ошибка: Невозможно добавить пустой контракт.");
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
        return new ArrayList<>(contracts); // Возвращаем копию списка для защиты от внешних изменений
    }

    /**
     * Проверяет контракты, срок действия которых истекает в ближайшие 30 дней.
     * Выводит список таких контрактов в консоль.
     * Если таких контрактов нет, выводится соответствующее сообщение.
     */
    public void checkExpiringContracts() {
        LocalDate today = LocalDate.now();
        LocalDate threshold = today.plusDays(29);

        List<Contract> expiringContracts = contracts.stream()
                .filter(contract -> contract.getEndDate() != null &&
                        !contract.getEndDate().isBefore(today) &&
                        contract.getEndDate().isBefore(threshold.plusDays(1)))
                .collect(Collectors.toList());

        if (expiringContracts.isEmpty()) {
            System.out.println("Нет контрактов, срок действия которых истекает в ближайшие 30 дней.");
        } else {
            System.out.println("Контракты, срок действия которых истекает в ближайшие 30 дней:");
            expiringContracts.forEach(System.out::println);
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