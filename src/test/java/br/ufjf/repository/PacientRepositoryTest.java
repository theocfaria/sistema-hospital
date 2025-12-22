package br.ufjf.repository;

import br.ufjf.model.Pacient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

public class PacientRepositoryTest {
    @Test
    @DisplayName("Deve salvar e carregar a lista de pacientes corretamente")
    void testSaveAndLoad() {
        PacientRepository repo = new PacientRepository();
        List<Pacient> list = new ArrayList<>();

        list.add(new Pacient("Test User", "123.456.789-00", "password"));

        repo.saveAll(list);
        List<Pacient> loadedList = repo.loadAll();

        assertNotNull(loadedList);
        assertEquals(1, loadedList.size());
        assertEquals("Test User", loadedList.get(0).getName());
    }
}
