package br.ufjf.repository;

import br.ufjf.model.Receptionist;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReceptionistRepositoryTest {

    @Test
    @DisplayName("ReceptionistRepository deve instanciar corretamente")
    void testRepository() {
        ReceptionistRepository repository = new ReceptionistRepository();
        assertNotNull(repository);

        assertNotNull(repository.loadAll());
    }
}
