import de.ait.Model.Contract;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContractTest {

    @Test
    void testValidContractCreation() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(30), "Условия");
        assertNotNull(contract.getId());
        assertEquals("Артист", contract.getArtistName());
        assertEquals("Условия", contract.getTerms());
    }

    @Test
    void testIsActive() {
        Contract activeContract = new Contract("Артист", LocalDate.now().minusDays(1), LocalDate.now().plusDays(10), "Условия");
        assertTrue(activeContract.isActive());

        Contract inactiveContract = new Contract("Артист", LocalDate.now().minusDays(30), LocalDate.now().minusDays(1), "Условия");
        assertFalse(inactiveContract.isActive());
    }

    @Test
    void testDaysUntilExpiration() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(10), "Условия");
        assertEquals(10, contract.daysUntilExpiration());
    }

    @Test
    void testSetArtistName() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(10), "Условия");
        contract.setArtistName("Новый Артист");
        assertEquals("Новый Артист", contract.getArtistName());
    }

    @Test
    void testSetInvalidArtistName() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(10), "Условия");
        contract.setArtistName("");
        assertEquals("Артист", contract.getArtistName()); // Значение не должно измениться
    }

    @Test
    void testSetStartDate() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(10), "Условия");
        contract.setStartDate(LocalDate.now().minusDays(5));
        assertEquals(LocalDate.now().minusDays(5), contract.getStartDate());
    }

    @Test
    void testSetInvalidStartDate() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(10), "Условия");
        contract.setStartDate(LocalDate.now().plusDays(20));
        assertEquals(LocalDate.now(), contract.getStartDate()); // Значение не должно измениться
    }

    @Test
    void testSetEndDate() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(10), "Условия");
        contract.setEndDate(LocalDate.now().plusDays(20));
        assertEquals(LocalDate.now().plusDays(20), contract.getEndDate());
    }

    @Test
    void testSetInvalidEndDate() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(10), "Условия");
        contract.setEndDate(LocalDate.now().minusDays(5));
        assertEquals(LocalDate.now().plusDays(10), contract.getEndDate()); // Значение не должно измениться
    }

    @Test
    void testToString() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(10), "Условия");
        String expected = "Contract{id='" + contract.getId() +
                "', artistName='Артист', startDate=" + LocalDate.now() +
                ", endDate=" + LocalDate.now().plusDays(10) +
                ", terms='Условия'}";
        assertEquals(expected, contract.toString());
    }

    @Test
    void testSetInvalidStartDateEdgeCase() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(10), "Условия");
        contract.setStartDate(null); // Проверяем поведение при null
        assertEquals(LocalDate.now(), contract.getStartDate());
    }

    @Test
    void testSetInvalidEndDateEdgeCase() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(10), "Условия");
        contract.setEndDate(null); // Проверяем поведение при null
        assertEquals(LocalDate.now().plusDays(10), contract.getEndDate());
    }

    @Test
    void testIsActiveEdgeCases() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now(), "Условия");
        assertTrue(contract.isActive()); // Дата начала и окончания — сегодня

        Contract contractFuture = new Contract("Артист", LocalDate.now().plusDays(1), LocalDate.now().plusDays(1), "Условия");
        assertFalse(contractFuture.isActive()); // Контракт начинается завтра
    }

    @Test
    void testSetStartDateSameAsEndDate() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now(), "Условия");
        contract.setStartDate(LocalDate.now()); // Должно сработать корректно
        assertEquals(LocalDate.now(), contract.getStartDate());
    }

    @Test
    void testSetTermsValid() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(10), "Условия");
        contract.setTerms("Новые условия");
        assertEquals("Новые условия", contract.getTerms()); // Проверяем, что значение обновилось
    }

    @Test
    void testSetTermsEmpty() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(10), "Условия");
        contract.setTerms(""); // Передаём пустую строку
        assertEquals("Условия", contract.getTerms()); // Значение должно остаться прежним
    }

    @Test
    void testSetTermsNull() {
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(10), "Условия");
        contract.setTerms(null); // Передаём null
        assertEquals("Условия", contract.getTerms()); // Значение должно остаться прежним
    }
}
