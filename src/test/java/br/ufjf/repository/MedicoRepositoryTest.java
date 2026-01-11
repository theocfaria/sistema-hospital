package br.ufjf.repository;

import br.ufjf.model.Medico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MedicoRepositoryTest {
    @Test
    @DisplayName("Deve salvar e carregar a lista de medicos corretamente")
    void testSaveAndLoad() {
        MedicoRepository repo = new MedicoRepository();
        List<Medico> list = new ArrayList<>();

        list.add(new Medico("Dr.User1", "123.456.789-00", "password",null, null, null, null));

        repo.saveAll(list);
        List<Medico> loadedList = repo.loadAll();

        assertNotNull(loadedList);
        assertEquals(1, loadedList.size());
        assertEquals("Dr.User1", loadedList.get(0).getName());
    }

    @Test
    @DisplayName("Deve buscar medico pelo id")
    void testFindByID() {
        List<Medico> list = new ArrayList<>();

        Medico p1 = new Medico("Dr.User1", "123.456.789-00", "password",null, null, null, null);
        Medico p2 = new Medico("Dr.User2", "987.654.321-00", "password",null, null, null, null);

        list.add(p1);
        list.add(p2);

        MedicoRepository repo = new MedicoRepository() {
            @Override
            public List<Medico> loadAll() {
                return List.of(p1, p2);
            }
        };

        String id = list.get(1).getId();

        Medico medico = repo.findByID(id.toString());

        assertNotNull(medico);
        assertEquals(id, medico.getId());
        assertEquals("Dr.User2", medico.getName());
    }

    @Test
    @DisplayName("Deve buscar medico pelo cpf")
    void testFindByCPF() {
        List<Medico> list = new ArrayList<>();

        Medico p1 = new Medico("Dr.User1", "123.456.789-00", "password",null, null, null, null);
        Medico p2 = new Medico("Dr.User2", "987.654.321-00", "password",null, null, null, null);

        list.add(p1);
        list.add(p2);

        MedicoRepository repo = new MedicoRepository() {
            @Override
            public List<Medico> loadAll() {
                return List.of(p1, p2);
            }
        };

        String cpf = list.get(1).getCpf();

        Medico medico = repo.findByCPF(cpf);

        assertNotNull(medico);
        assertEquals(cpf, medico.getCpf());
        assertEquals("Dr.User2", medico.getName());
    }

    @Test
    @DisplayName("Deve atualizar medico")
    void testUpdate() {
        List<Medico> list = new ArrayList<>();

        Medico p1 = new Medico("Dr.User1", "123.456.789-00", "password",null, null, null, null);

        list.add(p1);

        MedicoRepository repo = new MedicoRepository() {
            @Override
            public List<Medico> loadAll() {
                return List.of(p1);
            }
        };

        repo.updateByID(p1.getId().toString(), "Murilo", "999");

        assertNotNull(repo.findByID(p1.getId().toString()));
        assertEquals("Murilo", list.get(0).getName());
        assertEquals("999", list.get(0).getPassword());
    }

    @Test
    @DisplayName("Deve deletar um medico passado seu ID")
    void testDeleteByID() {
        List<Medico> list = new ArrayList<>();

        Medico p1 = new Medico("Dr.User1", "123.456.789-00", "password",null, null, null, null);
        Medico p2 = new Medico("Dr.User2", "987.654.321-00", "password",null, null, null, null);

        list.add(p1);
        list.add(p2);

        MedicoRepository repo = new MedicoRepository() {
            @Override
            public List<Medico> loadAll() {
                return list;
            }

            @Override
            public void saveAll(List<Medico> novaLista) {
                list.clear();
                list.addAll(novaLista);
            }
        };

        repo.deleteByID(p1.getId().toString());

        assertNotNull(list.get(0));
        assertEquals("Dr.User2", list.get(0).getName().toString());
    }

    @Test
    @DisplayName("Deve deletar um medico passado seu CPF")
    void testDeleteByCPF() {
        List<Medico> list = new ArrayList<>();

        Medico p1 = new Medico("Dr.User1", "222.222.222-22", "password",null, null, null, null);
        Medico p2 = new Medico("Dr.User2", "123.456.789-00", "password",null, null, null, null);

        list.add(p1);
        list.add(p2);

        MedicoRepository repo = new MedicoRepository() {
            @Override
            public List<Medico> loadAll() {
                return list;
            }

            @Override
            public void saveAll(List<Medico> novaLista) {
                list.clear();
                list.addAll(novaLista);
            }
        };

        repo.deleteByCpf(p1.getCpf());

        assertEquals(1, list.size());
        assertEquals("Dr.User2", list.get(0).getName().toString());
    }
}
