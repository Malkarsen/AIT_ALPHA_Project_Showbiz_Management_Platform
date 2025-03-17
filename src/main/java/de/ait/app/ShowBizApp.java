package de.ait.app;

import lombok.extern.slf4j.Slf4j;


import java.util.NoSuchElementException;
import java.util.Scanner;

@Slf4j
public class ShowBizApp {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        byte choice;
        boolean running = true;

        while (running) {
            showMenu();
            choice = inputChoice();

            switch (choice) {
                case 1 -> {
                    CastingManagerApp castingManager = new CastingManagerApp();
                    boolean keepRunning = castingManager.start();
                    if (!keepRunning) break;
                }
                case 2 -> {
                    ContractManagerApp contractManager = new ContractManagerApp();
                    boolean keepRunning = contractManager.start();
                    if (!keepRunning) break;
                }
                case 3 -> {
                    EventManagerApp eventManager = new EventManagerApp();
                    boolean keepRunning = eventManager.start();
                    if (!keepRunning) break;
                }
                case 4 -> {
                    FinanceManagerApp financeManager = new FinanceManagerApp();
                    boolean keepRunning = financeManager.start();
                    if (!keepRunning) break;
                }
                case 5 -> {

                    log.info("Exiting the general menu program.");
                    System.out.println("Exiting the program.");
                    sc.close();
                    running = false;
                }

                default -> {
                    System.out.println("Invalid choice. Please select a number between 1 and 5.");
                    log.warn("Invalid choice in main menu. Please try again.");
                }
            }
        }
    }

    private static byte inputChoice() {
        try {
            byte choice = sc.nextByte();
            sc.nextLine();
            return choice;
        } catch (NoSuchElementException e) {
            log.warn("Invalid input");
            System.out.println("Invalid input");
            return 0;
        }
    }

    private static void showMenu() {
        System.out.println("ShowBizApp:");
        System.out.println("1. Casting Manager");
        System.out.println("2. Contract Manager");
        System.out.println("3. Event Manager");
        System.out.println("4. Finance Manager");
        System.out.println("5. Exit");

        System.out.print("Enter your choice: ");

    }
}