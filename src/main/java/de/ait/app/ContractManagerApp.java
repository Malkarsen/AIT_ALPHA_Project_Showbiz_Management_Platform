package de.ait.app;

import de.ait.service.ContractManagerImpl;
import de.ait.model.Contract;
import de.ait.utilities.ContractTerms;
import lombok.extern.slf4j.Slf4j;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Slf4j

public class ContractManagerApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final ContractManagerImpl contraсtManager = new ContractManagerImpl();
    private static boolean runProgram = true;

    public static void main(String[] args) {
        ContractManagerApp app = new ContractManagerApp();
        app.start();
    }

    public boolean start() {
        while (runProgram) {
            showMenu();
            byte choice = sc.nextByte();
            sc.nextLine();

            switch (choice) {
                case 1 -> createNewContract();
                case 2 -> manageExistingContract();
                case 3 -> contraсtManager.displayAllContracts();
                case 4 -> {
                    runProgram = false;
                    log.info("Quit the program ContractManagerApp");
                    System.out.println("Exiting the program.");
                    return false;
                }
                default -> {
                    System.out.println("Invalid input. Please try again.");
                    log.warn("Invalid command entered in the main menu.");
                }
            }
        }
        return true;
    }

    private static void createNewContract() {
        try {
            Contract contract = buildContract();
            if (contract != null) {
                contraсtManager.addContract(contract);
            } else {
                log.warn("Failed to create contract due to invalid data.");
            }
        } catch (DateTimeException e) {
            System.out.println("Error: Invalid date format!");
            log.error("Date format error when creating a contract.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid contract data!");
            log.error("Contract creation error: {}", e.getMessage());
        }
    }

    private static void manageExistingContract() {
        System.out.println("Enter contract ID to manage:");
        String contractId = sc.nextLine().trim();
        Contract contract = findContractById(contractId);

        if (contract == null) {
            System.out.println("No contract found with this ID.");
            log.warn("Attempt to find a non-existent contract with ID {}", contractId);
            return;
        }

        boolean runContractMenu = true;
        while (runContractMenu) {
            showContractMenu();
            byte choice = sc.nextByte();
            sc.nextLine(); // Clear buffer

            switch (choice) {
                case 1 -> updateContractTerms(contract);
                case 2 -> checkContractExpiration(contract);
                case 3 -> {
                    runContractMenu = false;
                    log.info("Exiting contract management menu.");
                    System.out.println("Exiting contract menu.");
                }
                default -> {
                    System.out.println("Invalid input. Please try again.");
                    log.warn("Invalid input in contract management menu.");
                }
            }
        }
    }

    private static Contract buildContract() {
        System.out.println("Enter artist's name:");
        String artistName = sc.nextLine().trim();

        System.out.println("Enter contract start date (dd.MM.yyyy):");
        String startDateInput = sc.nextLine().trim();
        System.out.println("Enter contract end date (dd.MM.yyyy):");
        String endDateInput = sc.nextLine().trim();

        // Вывод доступных enum значений
        System.out.println("Available contract terms:");
        for (ContractTerms term : ContractTerms.values()) {
            System.out.println("- " + term.name());
        }
        System.out.println("Enter contract terms (as listed above):");
        String termsInput = sc.nextLine().trim().toUpperCase();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(startDateInput, formatter);
        LocalDate endDate = LocalDate.parse(endDateInput, formatter);

        try {
            ContractTerms terms = ContractTerms.valueOf(termsInput);
            return new Contract(artistName, startDate, endDate, terms);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid contract terms entered.");
            log.error("Invalid contract terms: {}", termsInput);
            return null;
        }
    }

    private static Contract findContractById(String contractId) {
        return contraсtManager.getContracts().stream()
                .filter(contract -> contract.getId().equals(contractId))
                .findFirst()
                .orElse(null);
    }

    private static void updateContractTerms(Contract contract) {
        System.out.println("Available contract terms:");
        for (ContractTerms term : ContractTerms.values()) {
            System.out.println("- " + term.name());
        }
        System.out.println("Enter new contract terms:");
        String newTermsInput = sc.nextLine().trim().toUpperCase();

        try {
            ContractTerms newTerms = ContractTerms.valueOf(newTermsInput);
            contract.setTerms(newTerms);
            log.info("Contract terms updated for {}", contract.getId());
            System.out.println("Contract terms updated.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid contract terms entered.");
            log.error("Error updating contract terms: {}", newTermsInput);
        }
    }

    private static void checkContractExpiration(Contract contract) {
        System.out.println("Contract ID: " + contract.getId());
        System.out.println("End date: " + contract.getEndDate());
        System.out.println("Days until expiration: " + contract.daysUntilExpiration());

        if (contract.daysUntilExpiration() <= 30) {
            System.out.println("⚠️ Contract is expiring soon!");
            log.warn("Contract {} is expiring in {} days.", contract.getId(), contract.daysUntilExpiration());
        } else {
            System.out.println("The contract is still valid.");
            log.info("Contract {} is still active.", contract.getId());
        }
    }

    private static void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Create a new contract");
        System.out.println("2. Manage an existing contract");
        System.out.println("3. View all contracts");
        System.out.println("4. Exit");
        System.out.print("Choose an action: ");
    }

    private static void showContractMenu() {
        System.out.println("\nContract Management Menu:");
        System.out.println("1. Update contract terms");
        System.out.println("2. Check contract expiration date");
        System.out.println("3. Return to the main menu");
        System.out.print("Choose an action: ");
    }
}
