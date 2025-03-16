package de.ait.app;

import de.ait.service.ContractManager;
import de.ait.model.Contract;
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
                    log.info("Program terminated by user.");
                    System.out.println("Exiting the program.");
                }
                default -> {
                    System.out.println("Invalid input. Please try again.");
                    log.warn("Invalid command entered in the main menu.");
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
        String contractId = scanner.nextLine().trim();
        Contract contract = findContractById(contractId);

        if (contract == null) {
            System.out.println("No contract found with this ID.");
            log.warn("Attempt to find a non-existent contract with ID {}", contractId);
            return;
        }

        boolean runContractMenu = true;
        while (runContractMenu) {
            showContractMenu();
            byte choice = scanner.nextByte();
            scanner.nextLine(); // Clear buffer

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
        String artistName = scanner.nextLine().trim();

        System.out.println("Enter contract start date (dd.MM.yyyy):");
        String startDateInput = scanner.nextLine().trim();
        System.out.println("Enter contract end date (dd.MM.yyyy):");
        String endDateInput = scanner.nextLine().trim();

        System.out.println("Enter contract terms:");
        String terms = scanner.nextLine().trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(startDateInput, formatter);
        LocalDate endDate = LocalDate.parse(endDateInput, formatter);

        try {
            return new Contract(artistName, startDate, endDate, terms);
        } catch (IllegalArgumentException e) {
            System.out.println("Error while creating the contract.");
            log.error("Contract creation error: {}", e.getMessage());
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
        System.out.println("Enter new contract terms:");
        String newTerms = scanner.nextLine().trim();

        try {
            contract.setTerms(newTerms);
            log.info("Contract terms updated for {}", contract.getId());
            System.out.println("Contract terms updated.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Terms cannot be empty.");
            log.error("Error updating contract terms: {}", e.getMessage());
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
