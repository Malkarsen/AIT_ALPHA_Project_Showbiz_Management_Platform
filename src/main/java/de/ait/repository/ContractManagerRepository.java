package de.ait.repository;

import de.ait.model.Contract;

import java.util.List;

/**
 * Manages a collection of contracts and provides methods for basic operations.
 */
public interface ContractManagerRepository {

    /**
     * Adds a contract to the list.
     * @param contract the contract to be added
     */
    void addContract(Contract contract);

    /**
     * Prints all existing contracts to the console.
     */
    void displayAllContracts();

    /**
     * Retrieves a list of all contracts.
     * @return list of contracts
     */
    List<Contract> getContracts();

    /**
     * Checks contracts expiring within the next 30 days.
     */
    void checkExpiringContracts();

    /**
     * Notifies the user about contracts expiring soon.
     */
    void notifyExpiringContracts();
}
