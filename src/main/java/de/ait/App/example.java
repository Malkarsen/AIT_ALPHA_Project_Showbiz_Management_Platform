package de.ait.App;

import java.time.LocalDate;

public class example {

    public static void main(String[] args) {

        // Создаем объект ContractManager и тестируем функционал и логику
        ContractManager manager = new ContractManager();

        // Тестовые контракты с обработкой исключений
        try {
            manager.addContract(new Contract("Артист 1", LocalDate.now().minusDays(5), LocalDate.now().plusDays(10), "Условие 1"));
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка при добавлении контракта: " + e.getMessage());
        }

        try {
            manager.addContract(new Contract("Артист 2", LocalDate.now(), LocalDate.now().plusDays(40), "Условие 2"));
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка при добавлении контракта: " + e.getMessage());
        }

        try {
            manager.addContract(new Contract("Артист 3", LocalDate.now().plusDays(1), LocalDate.now().plusDays(20), "Условие 3"));
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка при добавлении контракта: " + e.getMessage());
        }

        // Автоматически уведомляем об истекающих контрактах
        manager.notifyExpiringContracts();
    }
}
