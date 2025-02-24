package de.ait.core;

import de.ait.model.Contract;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * The ContractManager class manages a list of contracts.
 * It provides methods for adding, displaying, and checking contracts.
 */
@Slf4j
public class ContractManager {
    private final List<Contract> contracts; // Using final since the collection itself does not change

    /**
     * Constructor for creating a new contract manager.
     * Initializes an empty contract collection.
     */
    public ContractManager() {
        this.contracts = new ArrayList<>();
    }

    /**
     * Adds a new contract to the list.
     *
     * @param contract the contract to add.
     *                 If the provided contract is null, an error message is logged, and an exception is thrown.
     */
    public void addContract(Contract contract) {
        if (contract == null) {
            log.error("Error: Cannot add a null contract.");
            throw new IllegalArgumentException("Error: Cannot add a null contract.");
        }
        contracts.add(contract);
        log.info("Contract added: {}", contract.getId());
    }

    /**
     * Displays a list of all contracts.
     * If the list is empty, a corresponding message is displayed.
     */
    public void displayAllContracts() {
        if (contracts.isEmpty()) {
            log.info("The contract list is empty.");
            System.out.println("The contract list is empty.");
            return;
        }
        System.out.println("List of all contracts:");
        contracts.forEach(System.out::println);
    }

    /**
     * Returns a list of all contracts.
     *
     * @return a copy of the contract list to prevent external modifications.
     */
    public List<Contract> getContracts() {
        return new ArrayList<>(contracts);
    }

    /**
     * Checks for contracts that are expiring within the next 30 days.
     * Displays such contracts in the console. If none are found, an appropriate message is displayed.
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
            log.info("No contracts are expiring within the next 30 days.");
            System.out.println("No contracts are expiring within the next 30 days.");
        } else {
            log.info("Contracts expiring within the next 30 days: {}", expiringContracts.size());
            System.out.println("Contracts expiring within the next 30 days:");
            expiringContracts.forEach(System.out::println);
        }
    }

    /**
     * Notifies about expiring contracts when the program starts.
     * Calls {@link #checkExpiringContracts()} to perform the check.
     */
    public void notifyExpiringContracts() {
        log.info("Checking contracts for expiration...");
        System.out.println("Checking contracts for expiration...");
        checkExpiringContracts();
    }
}