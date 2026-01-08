package br.ufjf.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.ufjf.model.Pacient;

public class PacientRepositoryTest {
    /*
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
    void testFindByID() {
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
    void testFindByCPF() {
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

    @Test
    @DisplayName("Deve atualizar paciente")
    void testUpdate() {
        List<Pacient> list = new ArrayList<>();

        Pacient p1 = new Pacient("Test User1", "123.456.789-00", "password");

        list.add(p1);

        PacientRepository repo = new PacientRepository() {
            @Override
            public List<Pacient> loadAll() {
                return List.of(p1);
            }
        };

        repo.updateByID(p1.getId().toString(), "Murilo", "123");

        assertNotNull(repo.findByID(p1.getId().toString()));
        assertEquals("Murilo", list.get(0).getName());
        assertEquals("123", list.get(0).getPassword());
    }

    @Test
    @DisplayName("Deve deletar um paciente passado seu ID")
    void testDeleteByID() {
        List<Pacient> list = new ArrayList<>();

        Pacient p1 = new Pacient("Test User1", "123.456.789-00", "password");
        Pacient p2 = new Pacient("Test User2", "123.456.789-00", "password");

        list.add(p1);
        list.add(p2);

        PacientRepository repo = new PacientRepository() {
            @Override
            public List<Pacient> loadAll() {
                return list;
            }

            @Override
            public void saveAll(List<Pacient> novaLista) {
                list.clear();
                list.addAll(novaLista);
            }
        };

        repo.deleteByID(p1.getId().toString());

        assertNotNull(list.get(0));
        assertEquals("Test User2", list.get(0).getName().toString());
    }

    @Test
    @DisplayName("Deve deletar um paciente passado seu CPF")
    void testDeleteByCPF() {
        List<Pacient> list = new ArrayList<>();

        Pacient p1 = new Pacient("Test User1", "222.222.222-22", "password");
        Pacient p2 = new Pacient("Test User2", "123.456.789-00", "password");

        list.add(p1);
        list.add(p2);

        PacientRepository repo = new PacientRepository() {
            @Override
            public List<Pacient> loadAll() {
                return list;
            }

            @Override
            public void saveAll(List<Pacient> novaLista) {
                list.clear();
                list.addAll(novaLista);
            }
        };

        repo.deleteByCpf(p1.getCpf());

        assertEquals(1, list.size());
        assertEquals("Test User2", list.get(0).getName().toString());
    }
     */
}
