package de.ait.app;

import de.ait.core.*;
import de.ait.model.*;
import de.ait.repository.CastingManagerRepository;
import de.ait.repository.ContractManagerRepository;
import de.ait.utilities.*;
import de.ait.exceptions.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Slf4j
public class ShowBizApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EventManager eventManager = new EventManager();
    private static final FinanceManager financeManager = new FinanceManager();
    private static final CastingManagerRepository castingManager = new CastingManager();
    private static final ContractManagerRepository contractManager = new ContractManager();

    public static void main(String[] args) throws EventIsNotInListException, EventAlreadyInListException {
        boolean run = true;
        while (run) {
            showMainMenu();
            int choice = inputChoice();
            switch (choice) {
                case 1 -> manageEvents();
                case 2 -> manageFinances();
                case 3 -> manageCastings();
                case 4 -> manageContracts();
                case 5 -> {
                    run = false;
                    System.out.println("Exiting program.");
                    log.info("Program exited.");
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    private static void showMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Manage Events");
        System.out.println("2. Manage Finances");
        System.out.println("3. Manage Castings");
        System.out.println("4. Manage Contracts");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static int inputChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        return choice;
    }

    private static void manageEvents() throws EventAlreadyInListException, EventIsNotInListException {
        boolean run = true;
        while (run) {
            System.out.println("\nEvent Management:");
            System.out.println("1. Add Event");
            System.out.println("2. Delete Event");
            System.out.println("3. View Events");
            System.out.println("4. Return to Main Menu");
            System.out.print("Choose an option: ");
            int choice = inputChoice();

            switch (choice) {
                case 1 -> addEvent();
                case 2 -> deleteEvent();
                case 3 -> eventManager.displayAllEvents();
                case 4 -> run = false;
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addEvent() throws EventAlreadyInListException {
        System.out.print("Enter Event Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Event Type (CONCERT, SPORTS, THEATER, etc.): ");
        EventType type = EventType.valueOf(scanner.nextLine().trim().toUpperCase());
        System.out.print("Enter Event Date (dd.MM.yyyy): ");
        LocalDate date = LocalDate.parse(scanner.nextLine().trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        System.out.print("Enter Location: ");
        String location = scanner.nextLine().trim();
        System.out.print("Enter Ticket Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Event event = new Event(name, type, date, location, 100, price);
        eventManager.addEvent(event);
    }

    private static void deleteEvent() throws EventIsNotInListException {
        System.out.print("Enter Event ID to delete: ");
        String eventId = scanner.nextLine().trim();
        eventManager.removeEventById(eventId);
    }

    private static void manageFinances() {
        boolean run = true;
        while (run) {
            System.out.println("\nFinance Management:");
            System.out.println("1. Add Record");
            System.out.println("2. Show Records");
            System.out.println("3. Exit");
            int choice = inputChoice();
            switch (choice) {
                case 1 -> addFinanceRecord();
                case 2 -> financeManager.getFinanceRecords().forEach(System.out::println);
                case 3 -> run = false;
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addFinanceRecord() {
        System.out.print("Enter Type (INCOME/EXPENSE): ");
        RecordType type = RecordType.valueOf(scanner.nextLine().trim().toUpperCase());
        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Enter Date (dd.MM.yyyy): ");
        LocalDate date = LocalDate.parse(scanner.nextLine().trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        financeManager.addRecord(type, amount, description, date);
    }

    private static void manageCastings() {
        System.out.println("Casting management functionality here.");
    }

    private static void manageContracts() {
        System.out.println("Contract management functionality here.");
    }
}
