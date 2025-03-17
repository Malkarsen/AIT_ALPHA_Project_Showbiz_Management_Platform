package de.ait.service;

import de.ait.model.Contract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ContractManagerTest {

    private ContractManager contractManager;

    @BeforeEach
    void setUp() {
        // Initialize a new instance of ContractManager before each test
        contractManager = new ContractManager();
    }

    @Test
    void testAddContract() {
        // Creating a contract
        Contract contract = new Contract("Artist A", LocalDate.now(), LocalDate.now().plusDays(60), "Terms");

        // Adding a contract
        contractManager.addContract(contract);

        // Check that the contract has been added
        List<Contract> contracts = contractManager.getContracts();
        assertEquals(1, contracts.size(), "The list must contain one contract");
        assertEquals(contract, contracts.get(0), "The added contract must match the original contract");

        // Check that getContracts returns a copy and not the original
        contracts.clear(); // Modify the returned list
        assertEquals(1, contractManager.getContracts().size(), "The original list should not change after the copy has been modified");
    }

    @Test
    void testAddNullContractThrowsException() {
        // Ensure that adding a null contract throws an exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> contractManager.addContract(null));
        assertEquals("Error: Cannot add a null contract.", exception.getMessage());
    }

    @Test
    void testCheckExpiringContractsWithin30Days() {
        // Test checking for contracts that expire within the next 30 days
        Contract expiringContract = new Contract("Artist B", LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), "Terms");
        Contract notExpiringContract = new Contract("Artist C", LocalDate.now().minusDays(30), LocalDate.now().plusDays(40), "Terms");

        contractManager.addContract(expiringContract);
        contractManager.addContract(notExpiringContract);

        List<Contract> contracts = contractManager.getContracts();
        assertEquals(2, contracts.size());

        assertDoesNotThrow(() -> contractManager.checkExpiringContracts());
    }

    @Test
    void testCheckExpiringContractsWhenNoneExist() {
        // Ensure that checking for expiring contracts when none exist does not cause an exception
        assertDoesNotThrow(() -> contractManager.checkExpiringContracts());
    }

    @Test
    void testDisplayAllContracts() {
        // Test displaying all contracts stored in the contract manager
        Contract contract1 = new Contract("Artist A", LocalDate.now(), LocalDate.now().plusDays(30), "Terms A");
        Contract contract2 = new Contract("Artist B", LocalDate.now().plusDays(10), LocalDate.now().plusDays(40), "Terms B");

        contractManager.addContract(contract1);
        contractManager.addContract(contract2);

        assertDoesNotThrow(() -> contractManager.displayAllContracts());
    }

    @Test
    void testNotifyExpiringContracts() {
        // Creating a contract that's about to expire
        Contract expiringContract = new Contract("Artist D",
                LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(5),
                "Terms");

        // Adding a contract to the manager
        contractManager.addContract(expiringContract);

        // Get the list of contracts and check that the contract has been added
        List<Contract> contracts = contractManager.getContracts();
        assertEquals(1, contracts.size(), "There should be exactly one contract.");

        // Check that the notification method does not raise an exception
        assertDoesNotThrow(() -> contractManager.notifyExpiringContracts());
    }
}
