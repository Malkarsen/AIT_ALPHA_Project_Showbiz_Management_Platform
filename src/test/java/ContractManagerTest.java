import de.ait.Core.ContractManager;
import de.ait.Model.Contract;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContractManagerTest {

    @Test
    void testAddContract() {
        ContractManager manager = new ContractManager();
        Contract contract = new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(30), "Условия");
        manager.addContract(contract);

        assertEquals(1, manager.getContracts().size());
        assertEquals("Артист", manager.getContracts().get(0).getArtistName());
    }

    @Test
    void testAddNullContract() {
        ContractManager manager = new ContractManager();
        manager.addContract(null);
        assertEquals(0, manager.getContracts().size());
    }

    @Test
    void testDisplayAllContracts() {
        ContractManager manager = new ContractManager();
        manager.addContract(new Contract("Артист 1", LocalDate.now(), LocalDate.now().plusDays(30), "Условия 1"));
        manager.addContract(new Contract("Артист 2", LocalDate.now(), LocalDate.now().plusDays(40), "Условия 2"));

        assertEquals(2, manager.getContracts().size());
    }

    @Test
    void testCheckExpiringContracts() {
        ContractManager manager = new ContractManager();
        manager.addContract(new Contract("Артист 1", LocalDate.now(), LocalDate.now().plusDays(10), "Условия 1"));
        manager.addContract(new Contract("Артист 2", LocalDate.now(), LocalDate.now().plusDays(40), "Условия 2"));

        // Проверяем, что истекающий контракт определён корректно
        manager.checkExpiringContracts();
        assertEquals(2, manager.getContracts().size());
    }

    @Test
    void testNotifyExpiringContracts() {
        ContractManager manager = new ContractManager();
        manager.addContract(new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(10), "Условия"));

        // Вызываем метод и проверяем, что он работает без ошибок
        manager.notifyExpiringContracts();
    }

    @Test
    void testEmptyContractList() {
        ContractManager manager = new ContractManager();
        manager.checkExpiringContracts(); // Проверяем, что программа корректно обрабатывает пустой список
        assertEquals(0, manager.getContracts().size());
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
    void testDisplayAllContractsEmptyList() {
        // Создаем пустой менеджер контрактов
        ContractManager manager = new ContractManager();

        // Вызываем метод
        manager.displayAllContracts();

        // Проверяем, что список контрактов пуст
        assertTrue(manager.getContracts().isEmpty());
    }

    @Test
    void testCheckExpiringContractsEmptyList() {
        // Создаем пустой менеджер контрактов
        ContractManager manager = new ContractManager();

        // Вызываем метод
        manager.checkExpiringContracts();

        // Проверяем, что список контрактов пуст
        assertTrue(manager.getContracts().isEmpty());
    }

    @Test
    void testCheckExpiringContractsWithSingleContract() {
        ContractManager manager = new ContractManager();
        manager.addContract(new Contract("Артист", LocalDate.now(), LocalDate.now().plusDays(1), "Условия"));
        manager.checkExpiringContracts();
        // Убедитесь, что метод не выбрасывает ошибок и корректно обрабатывает один контракт.
    }
}
