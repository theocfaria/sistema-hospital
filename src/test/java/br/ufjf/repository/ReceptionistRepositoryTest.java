package br.ufjf.repository;
import br.ufjf.model.Medico;
import br.ufjf.model.Receptionist;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReceptionistRepositoryTest {

    @Test
    @DisplayName("Deve salvar e carregar a lista de recepcionistas corretamente")
    void testSaveAndLoad() {
        ReceptionistRepository repo = new ReceptionistRepository();
        List<Receptionist> list = new ArrayList<>();

        list.add(new Receptionist("Test User", "123.456.789-00", "password"));

        repo.saveAll(list);
        List<Receptionist> loadedList = repo.loadAll();

        assertNotNull(loadedList);
        assertEquals(1, loadedList.size());
        assertEquals("Test User", loadedList.get(0).getName());
    }

    @Test
    @DisplayName("Deve buscar Recepcionista pelo id")
    void testFindByID() {
        Receptionist r1 = new Receptionist("Test User1", "123.456.789-00", "password");
        Receptionist r2 = new Receptionist("Test User2", "987.654.321-00", "password");


        ReceptionistRepository repo = new ReceptionistRepository(){
            @Override
            public List<Receptionist> loadAll(){
                return List.of(r1,r2);
            };
        };

        UUID id = r2.getId();

        Receptionist receptionist = repo.findByID(id.toString());

        assertNotNull(receptionist);
        assertEquals(id, receptionist.getId());
        assertEquals("Test User2", receptionist.getName());
    }

    @Test
    @DisplayName("Deve buscar recepcionista pelo cpf")
    void testFindByCPF() {

        List<Receptionist> list = new ArrayList<>();

        Receptionist r1 = new Receptionist("Test User1", "123.4565.789-00", "password");
        Receptionist r2 = new Receptionist("Test User2", "987.654.432-11", "password");

        list.add(r1);
        list.add(r2);

        ReceptionistRepository repo = new ReceptionistRepository(){
            @Override
            public List<Receptionist> loadAll(){
                return List.of(r1,r2);
            }
        };

        String cpf = list.get(1).getCpf();
        Receptionist receptionist = repo.findByCPF(cpf);

        assertNotNull(receptionist);
        assertEquals(cpf, receptionist.getCpf());
        assertEquals("Test User2", receptionist.getName());
    }

    @Test
    @DisplayName("Deve atualizar recepcionista pelo ID")
    void testUpdateByID() {

        Receptionist r1 = new Receptionist("Test", "123.456.789-00", "password");
        ReceptionistRepository repo = new ReceptionistRepository();

        repo.saveAll(List.of(r1));

        repo.updateByID(r1.getId().toString(), "Ryan", "teste");

        Receptionist atualizado = repo.findByID(r1.getId().toString());

        assertNotNull(atualizado);
        assertEquals("Ryan", atualizado.getName());
        assertEquals("teste", atualizado.getPassword());
    }

    @Test
    @DisplayName("Deve deletar um recepcionista passado seu ID")
    void testDeleteByID() {
        List<Receptionist> list = new ArrayList<>();

        Receptionist p1 = new Receptionist("Test User1", "123.456.789-00", "password");
        Receptionist p2 = new Receptionist("Test User2", "123.456.789-00", "password");

        list.add(p1);
        list.add(p2);

        ReceptionistRepository repo = new ReceptionistRepository() {
            @Override
            public List<Receptionist> loadAll() {
                return list;
            }

            @Override
            public void saveAll(List<Receptionist> novaLista) {
                list.clear();
                list.addAll(novaLista);
            }
        };

        repo.deleteByID(p1.getId().toString());

        assertNotNull(list.get(0));
        assertEquals("Test User2", list.get(0).getName().toString());
    }

    @Test
    @DisplayName("Deve deletar um recepcionista passado seu CPF")
    void testDeleteByCPF() {
        List<Receptionist> list = new ArrayList<>();

        Receptionist p1 = new Receptionist("Test User1", "222.222.222-22", "password");
        Receptionist p2 = new Receptionist("Test User2", "123.456.789-00", "password");

        list.add(p1);
        list.add(p2);

        ReceptionistRepository repo = new ReceptionistRepository() {
            @Override
            public List<Receptionist> loadAll() {
                return list;
            }

            @Override
            public void saveAll(List<Receptionist> novaLista) {
                list.clear();
                list.addAll(novaLista);
            }
        };

        repo.deleteByCpf(p1.getCpf());

        assertEquals(1, list.size());
        assertEquals("Test User2", list.get(0).getName().toString());
    }
}
