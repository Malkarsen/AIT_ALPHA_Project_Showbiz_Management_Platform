package de.ait.service;

import de.ait.model.Contract;
import de.ait.utilities.ContractTerms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContractManagerTest {

    private ContractManagerImpl contractManager;

    @BeforeEach
    void setUp() {
        contractManager = new ContractManagerImpl();
    }

    @Test
    void testAddContract() {
        Contract contract = new Contract("Artist A", LocalDate.now(), LocalDate.now().plusDays(60), ContractTerms.STANDARD);
        contractManager.addContract(contract);

        List<Contract> contracts = contractManager.getContracts();
        assertEquals(1, contracts.size());
        assertEquals(contract, contracts.get(0));

        contracts.clear();
        assertEquals(1, contractManager.getContracts().size());
    }

    @Test
    void testAddNullContractThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> contractManager.addContract(null));
        assertEquals("Error: Cannot add a null contract.", exception.getMessage());
    }

    @Test
    void testCheckExpiringContractsWithin30Days() {
        Contract expiringContract = new Contract("Artist B", LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), ContractTerms.EXCLUSIVE);
        Contract notExpiringContract = new Contract("Artist C", LocalDate.now().minusDays(30), LocalDate.now().plusDays(40), ContractTerms.TEMPORARY);

        contractManager.addContract(expiringContract);
        contractManager.addContract(notExpiringContract);

        List<Contract> contracts = contractManager.getContracts();
        assertEquals(2, contracts.size());

        assertDoesNotThrow(() -> contractManager.checkExpiringContracts());
    }

    @Test
    void testCheckExpiringContractsWhenNoneExist() {
        assertDoesNotThrow(() -> contractManager.checkExpiringContracts());
    }

    @Test
    void testDisplayAllContracts() {
        Contract contract1 = new Contract("Artist A", LocalDate.now(), LocalDate.now().plusDays(30), ContractTerms.PAID);
        Contract contract2 = new Contract("Artist B", LocalDate.now().plusDays(10), LocalDate.now().plusDays(40), ContractTerms.CHARITY);

        contractManager.addContract(contract1);
        contractManager.addContract(contract2);

        assertDoesNotThrow(() -> contractManager.displayAllContracts());
    }

    @Test
    void testNotifyExpiringContracts() {
        Contract expiringContract = new Contract("Artist D",
                LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(5),
                ContractTerms.EXCLUSIVE);

        contractManager.addContract(expiringContract);

        List<Contract> contracts = contractManager.getContracts();
        assertEquals(1, contracts.size());

        assertDoesNotThrow(() -> contractManager.notifyExpiringContracts());
    }
}
