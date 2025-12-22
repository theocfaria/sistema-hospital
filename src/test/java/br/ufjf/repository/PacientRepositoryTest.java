package br.ufjf.repository;

import br.ufjf.model.Pacient;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

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

    @Test
    @DisplayName("Deve buscar paciente pelo id")
    void testFindByID(){
        List<Pacient> list = new ArrayList<>();

        Pacient p1 = new Pacient("Test User1", "123.456.789-00", "password");
        Pacient p2 = new Pacient("Test User2", "987.654.321-00", "password");

        list.add(p1);
        list.add(p2);

        PacientRepository repo = new PacientRepository() {
            @Override
            public List<Pacient> loadAll() {
                return List.of(p1, p2);
            }
        };

        UUID id = list.get(1).getId();

        Pacient paciente = repo.findByID(id.toString());

        assertNotNull(paciente);
        assertEquals(id, paciente.getId());
        assertEquals("Test User2", paciente.getName());
    }

    @Test
    @DisplayName("Deve buscar paciente pelo cpf")
    void testFindByCPF(){
        List<Pacient> list = new ArrayList<>();

        Pacient p1 = new Pacient("Test User1", "123.456.789-00", "password");
        Pacient p2 = new Pacient("Test User2", "987.654.321-00", "password");

        list.add(p1);
        list.add(p2);

        PacientRepository repo = new PacientRepository() {
            @Override
            public List<Pacient> loadAll() {
                return List.of(p1, p2);
            }
        };

        String cpf = list.get(1).getCpf();

        Pacient paciente = repo.findByCPF(cpf);

        assertNotNull(paciente);
        assertEquals(cpf, paciente.getCpf());
        assertEquals("Test User2", paciente.getName());

    }
}
