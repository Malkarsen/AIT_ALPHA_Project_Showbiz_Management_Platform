package de.ait.app;

import de.ait.service.CastingManager;
import de.ait.model.Casting;
import de.ait.model.Participant;
import de.ait.utilities.ParticipantStatus;
import de.ait.exceptions.NoRegisteredException;
import lombok.extern.slf4j.Slf4j;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Slf4j

public class CastingManagerApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final CastingManager castingManager = new CastingManager();
    private static Casting casting;
    private static boolean runCasting;

    public static void main(String[] args) {
        CastingManagerApp app = new CastingManagerApp();
        app.start();
    }

    public boolean start() {
        byte choice = 0;
        boolean run = true;
        while (run) {
            showMenu();
            choice = sc.nextByte();
            switch (choice) {
                case 1 -> {
                    try {
                        casting = buildCasting();
                        castingManager.registerCasting(casting);
                    } catch (DateTimeException exception) {
                        System.out.println("Date is null or in illegal format!");
                        log.error("Date is null or in illegal format!");
                    }
                }
                case 2 -> {
                    runCasting = true;
                    byte choiceCasting;
                    casting = acceptToFindCasting();
                    while (runCasting) {
                        showCastingMenu();
                        choiceCasting = sc.nextByte();
                        switch (choiceCasting) {
                            case 1 -> {
                                Participant participant = buildParticipant();
                                try {
                                    casting.registerParticipant(participant);
                                } catch (NoRegisteredException exception) {
                                    System.out.println("Participant data entered incorrectly");
                                }
                            }
                            case 2 -> {
                                acceptToUpdateParticipantStatus();
                            }
                            case 3 -> {
                                casting.showParticipants();
                            }
                            case 4 -> {
                                runCasting = false;
                                System.out.println("You are exiting casting menu");
                                log.info("Quit the casting menu");
                            }
                            default -> {
                                System.out.println("Input is incorrect");
                                log.warn("attempt to incorrect Casting menu input");
                            }
                        }
                    }
                }
                case 3 -> {
                    castingManager.showCastings();
                }
                case 4 -> {
                    run = false;
                    System.out.println("You are exiting the program");
                    log.info("Quit the program CastingManagerApp");
                    return false;
                }
                default -> {
                    System.out.println("Input is incorrect");
                    log.warn("attempt to incorrect Main menu input");
                }
            }
        }
        return true;
    }

    private static Participant buildParticipant() {
        sc.nextLine();

        //System.out.println("Enter Participant Id: ");
        //String id = scanner.nextLine().trim();
        System.out.println("Enter Participant Name: ");
        String name = sc.nextLine().trim();
        System.out.println("Enter Participant Status (NEW, IN_PROGRESS, REJECTED_CANDIDATE, APPROVED_CANDIDATE): ");
        String status = sc.nextLine().trim().toUpperCase();

        Participant participant = null;
        try {
            participant = new Participant(name, ParticipantStatus.valueOf(status));
        } catch (IllegalArgumentException exception) {
            System.out.println("Failed participant creation");
            log.error("Failed participant creation");
        }

        return participant;
    }

    private static Casting buildCasting() {
        sc.nextLine();

        //System.out.println("Enter Casting Id: ");
        //String id = scanner.nextLine().trim();
        System.out.println("Enter Casting name: ");
        String name = sc.nextLine().trim();
        System.out.println("Enter Casting description: ");
        String description = sc.nextLine().trim();
        System.out.println("Enter Casting location: ");
        String location = sc.nextLine().trim();
        System.out.println("Enter Casting date(dd.MM.yyyy): ");
        String userCastingDate = sc.nextLine().trim();
        DateTimeFormatter formatterUser = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate castingDate = LocalDate.parse(userCastingDate, formatterUser);
        System.out.println("Enter Casting new date(dd.MM.yyyy): ");
        String userNewDate = sc.nextLine().trim();
        LocalDate newDate = LocalDate.parse(userCastingDate, formatterUser);


        Casting casting = null;
        try {
            casting = new Casting(name, description, location, castingDate, newDate);
        } catch (IllegalArgumentException exception) {
            System.out.println("Failed Casting creation");
            log.error("Failed Casting creation");
        }

        return casting;
    }

    private static void acceptToUpdateParticipantStatus() {
        sc.nextLine();

        System.out.println("Enter Participant Id: ");
        String participantId = sc.nextLine().trim();
        System.out.println("Enter new status(NEW, IN_PROGRESS, REJECTED_CANDIDATE, APPROVED_CANDIDATE): ");
        String newStatus = sc.nextLine().trim().toUpperCase();

        try {
            casting.updateParticipantStatus(participantId, ParticipantStatus.valueOf(newStatus));
        } catch (IllegalArgumentException exception) {
            System.out.println("New status is null \n");
            log.error("attempt enter incorrect status");

        }
    }

    private static Casting acceptToFindCasting() {
        sc.nextLine();

        System.out.println("Enter Casting Id: ");
        String castingId = sc.nextLine().trim();
        casting = castingManager.findCasting(castingId);

        if (casting == null) {
            System.out.println("No casting found with such id");
            runCasting = false;
        }
        return casting;
    }

    private static void showMenu() {
        System.out.println("Menu: ");
        System.out.println("1. New Casting");
        System.out.println("2. Select Casting");
        System.out.println("3. View Castings");
        System.out.println("4. Quit \n");
    }

    private static void showCastingMenu() {
        System.out.println("1. Add participant");
        System.out.println("2. Change participant status");
        System.out.println("3. View participants");
        System.out.println("4. Quit \n");
    }
}
