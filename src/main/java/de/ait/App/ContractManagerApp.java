package de.ait.App;

import de.ait.Core.ContractManager;
import de.ait.Model.Contract;
import lombok.extern.slf4j.Slf4j;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
@Slf4j

public class ContractManagerApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ContractManager manager = new ContractManager();
    private static boolean runProgram = true;

    public static void main(String[] args) {
        while (runProgram) {
            showMenu();
            byte choice = scanner.nextByte();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createNewContract();
                case 2 -> manageExistingContract();
                case 3 -> manager.displayAllContracts();
                case 4 -> {
                    runProgram = false;
                    log.info("Программа завершена пользователем.");
                    System.out.println("Выход из программы.");
                }
                default -> {
                    System.out.println("Некорректный ввод. Попробуйте снова.");
                    log.warn("Попытка ввода некорректной команды в основном меню.");
                }
            }
        }
    }

    private static void createNewContract() {
        try {
            Contract contract = buildContract();
            if (contract != null) {
                manager.addContract(contract);
            } else {
                log.warn("Не удалось создать контракт из-за некорректных данных.");
            }
        } catch (DateTimeException e) {
            System.out.println("Ошибка: Неверный формат даты!");
            log.error("Ошибка формата даты при создании контракта.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: Некорректные данные контракта!");
            log.error("Ошибка создания контракта: {}", e.getMessage());
        }
    }

    private static void manageExistingContract() {
        System.out.println("Введите ID контракта для управления:");
        String contractId = scanner.nextLine().trim();
        Contract contract = findContractById(contractId);

        if (contract == null) {
            System.out.println("Контракт с таким ID не найден.");
            log.warn("Попытка найти несуществующий контракт с ID {}", contractId);
            return;
        }

        boolean runContractMenu = true;
        while (runContractMenu) {
            showContractMenu();
            byte choice = scanner.nextByte();
            scanner.nextLine(); // очистка буфера

            switch (choice) {
                case 1 -> updateContractTerms(contract);
                case 2 -> checkContractExpiration(contract);
                case 3 -> {
                    runContractMenu = false;
                    log.info("Выход из меню управления контрактом.");
                    System.out.println("Выход из меню контракта.");
                }
                default -> {
                    System.out.println("Некорректный ввод. Попробуйте снова.");
                    log.warn("Некорректный ввод в меню управления контрактом.");
                }
            }
        }
    }

    private static Contract buildContract() {
        System.out.println("Введите имя артиста:");
        String artistName = scanner.nextLine().trim();

        System.out.println("Введите дату начала контракта (дд.ММ.гггг):");
        String startDateInput = scanner.nextLine().trim();
        System.out.println("Введите дату окончания контракта (дд.ММ.гггг):");
        String endDateInput = scanner.nextLine().trim();

        System.out.println("Введите условия контракта:");
        String terms = scanner.nextLine().trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(startDateInput, formatter);
        LocalDate endDate = LocalDate.parse(endDateInput, formatter);

        try {
            return new Contract(artistName, startDate, endDate, terms);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при создании контракта.");
            log.error("Ошибка при создании контракта: {}", e.getMessage());
            return null;
        }
    }

    private static Contract findContractById(String contractId) {
        return manager.getContracts().stream()
                .filter(contract -> contract.getId().equals(contractId))
                .findFirst()
                .orElse(null);
    }

    private static void updateContractTerms(Contract contract) {
        System.out.println("Введите новые условия контракта:");
        String newTerms = scanner.nextLine().trim();

        try {
            contract.setTerms(newTerms);
            log.info("Условия контракта {} обновлены.", contract.getId());
            System.out.println("Условия контракта обновлены.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: Условия не могут быть пустыми.");
            log.error("Ошибка обновления условий контракта: {}", e.getMessage());
        }
    }

    private static void checkContractExpiration(Contract contract) {
        System.out.println("Контракт ID: " + contract.getId());
        System.out.println("Дата окончания: " + contract.getEndDate());
        System.out.println("Дней до истечения: " + contract.daysUntilExpiration());

        if (contract.daysUntilExpiration() <= 30) {
            System.out.println("⚠️ Контракт скоро истекает!");
            log.warn("Контракт {} истекает через {} дней.", contract.getId(), contract.daysUntilExpiration());
        } else {
            System.out.println("Контракт пока действителен.");
            log.info("Контракт {} еще активен.", contract.getId());
        }
    }

    private static void showMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Создать новый контракт");
        System.out.println("2. Управлять существующим контрактом");
        System.out.println("3. Просмотреть все контракты");
        System.out.println("4. Выйти");
        System.out.print("Выберите действие: ");
    }

    private static void showContractMenu() {
        System.out.println("\nМеню управления контрактом:");
        System.out.println("1. Обновить условия контракта");
        System.out.println("2. Проверить дату истечения контракта");
        System.out.println("3. Выйти в главное меню");
        System.out.print("Выберите действие: ");
    }
}
