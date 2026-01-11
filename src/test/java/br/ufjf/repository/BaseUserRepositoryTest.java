package br.ufjf.repository;

import br.ufjf.model.Receptionist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BaseUserRepositoryTest {

    private ReceptionistRepository repository;
    private List<Receptionist> testData;

    @BeforeEach
    void setUp() {
        repository = new ReceptionistRepository();
        testData = new ArrayList<>();

        repository.saveAll(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve salvar e carregar lista de usuários corretamente")
    void testSaveAllAndLoadAll() {
        Receptionist r1 = new Receptionist("Usuario 1", "111.111.111-11", "senha1");
        Receptionist r2 = new Receptionist("Usuario 2", "222.222.222-22", "senha2");
        
        testData.add(r1);
        testData.add(r2);
        
        repository.saveAll(testData);
        List<Receptionist> loaded = repository.loadAll();
        
        assertNotNull(loaded);
        assertEquals(2, loaded.size());
        assertEquals("Usuario 1", loaded.get(0).getName());
        assertEquals("Usuario 2", loaded.get(1).getName());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando arquivo não existe")
    void testLoadAllWhenFileNotExists() {
        List<Receptionist> loaded = repository.loadAll();
        
        assertNotNull(loaded);
        assertTrue(loaded.isEmpty());
    }

    @Test
    @DisplayName("Deve salvar um único elemento usando save()")
    void testSave() {
        Receptionist r1 = new Receptionist("Novo Usuario", "333.333.333-33", "senha");
        
        repository.save(r1);
        List<Receptionist> loaded = repository.loadAll();
        
        assertEquals(1, loaded.size());
        assertEquals("Novo Usuario", loaded.get(0).getName());
        assertEquals("333.333.333-33", loaded.get(0).getCpf());
    }

    @Test
    @DisplayName("Deve buscar usuário pelo ID")
    void testFindByID() {
        Receptionist r1 = new Receptionist("Usuario 1", "111.111.111-11", "senha1");
        Receptionist r2 = new Receptionist("Usuario 2", "222.222.222-22", "senha2");
        
        repository.save(r1);
        repository.save(r2);
        
        String id = r2.getId();
        Receptionist found = repository.findByID(id);
        
        assertNotNull(found);
        assertEquals(id, found.getId());
        assertEquals("Usuario 2", found.getName());
    }

    @Test
    @DisplayName("Deve retornar null quando ID não existe")
    void testFindByIDNotFound() {
        Receptionist found = repository.findByID("id-inexistente");
        assertNull(found);
    }

    @Test
    @DisplayName("Deve buscar usuário pelo CPF")
    void testFindByCPF() {
        Receptionist r1 = new Receptionist("Usuario 1", "111.111.111-11", "senha1");
        Receptionist r2 = new Receptionist("Usuario 2", "222.222.222-22", "senha2");
        
        repository.save(r1);
        repository.save(r2);
        
        Receptionist found = repository.findByCPF("222.222.222-22");
        
        assertNotNull(found);
        assertEquals("222.222.222-22", found.getCpf());
        assertEquals("Usuario 2", found.getName());
    }

    @Test
    @DisplayName("Deve retornar null quando CPF não existe")
    void testFindByCPFNotFound() {
        Receptionist found = repository.findByCPF("999.999.999-99");
        assertNull(found);
    }

    @Test
    @DisplayName("Deve buscar usuário pelo nome exato")
    void testFindByName() {
        Receptionist r1 = new Receptionist("João Silva", "111.111.111-11", "senha1");
        Receptionist r2 = new Receptionist("Maria Santos", "222.222.222-22", "senha2");
        
        repository.save(r1);
        repository.save(r2);
        
        Receptionist found = repository.findByName("Maria Santos");
        
        assertNotNull(found);
        assertEquals("Maria Santos", found.getName());
        assertEquals("222.222.222-22", found.getCpf());
    }

    @Test
    @DisplayName("Deve retornar null quando nome exato não existe")
    void testFindByNameNotFound() {
        Receptionist found = repository.findByName("Nome Inexistente");
        assertNull(found);
    }

    @Test
    @DisplayName("Deve buscar múltiplos usuários por nome parcial")
    void testFindManyByName() {
        Receptionist r1 = new Receptionist("João Silva", "111.111.111-11", "senha1");
        Receptionist r2 = new Receptionist("João Santos", "222.222.222-22", "senha2");
        Receptionist r3 = new Receptionist("Maria Silva", "333.333.333-33", "senha3");
        
        repository.save(r1);
        repository.save(r2);
        repository.save(r3);
        
        List<Receptionist> found = repository.findManyByName("João");
        
        assertEquals(2, found.size());
        assertTrue(found.stream().anyMatch(u -> u.getName().equals("João Silva")));
        assertTrue(found.stream().anyMatch(u -> u.getName().equals("João Santos")));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nome parcial não encontra resultados")
    void testFindManyByNameNotFound() {
        List<Receptionist> found = repository.findManyByName("Inexistente");
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Deve atualizar usuário completo usando update()")
    void testUpdate() {
        Receptionist r1 = new Receptionist("Usuario Original", "111.111.111-11", "senha1");
        repository.save(r1);
        
        String originalId = r1.getId();
        Receptionist updated = new Receptionist("Usuario Atualizado", "111.111.111-11", "novaSenha");
        updated.setName("Usuario Atualizado");

        Receptionist toUpdate = repository.findByID(originalId);
        toUpdate.setName("Usuario Atualizado");
        toUpdate.setPassword("novaSenha");
        
        repository.update(toUpdate);
        
        Receptionist found = repository.findByID(originalId);
        assertNotNull(found);
        assertEquals("Usuario Atualizado", found.getName());
        assertEquals("novaSenha", found.getPassword());
    }

    @Test
    @DisplayName("Deve atualizar nome e senha usando updateByID()")
    void testUpdateByID() {
        Receptionist r1 = new Receptionist("Usuario Original", "111.111.111-11", "senha1");
        repository.save(r1);
        
        String id = r1.getId();
        repository.updateByID(id, "Novo Nome", "novaSenha");
        
        Receptionist updated = repository.findByID(id);
        assertNotNull(updated);
        assertEquals("Novo Nome", updated.getName());
        assertEquals("novaSenha", updated.getPassword());
        assertEquals("111.111.111-11", updated.getCpf());
    }

    @Test
    @DisplayName("Deve deletar usuário pelo ID")
    void testDeleteByID() {
        Receptionist r1 = new Receptionist("Usuario 1", "111.111.111-11", "senha1");
        Receptionist r2 = new Receptionist("Usuario 2", "222.222.222-22", "senha2");
        
        repository.save(r1);
        repository.save(r2);
        
        String idToDelete = r1.getId();
        repository.deleteByID(idToDelete);
        
        List<Receptionist> remaining = repository.loadAll();
        assertEquals(1, remaining.size());
        assertEquals("Usuario 2", remaining.get(0).getName());
        assertNull(repository.findByID(idToDelete));
    }

    @Test
    @DisplayName("Deve deletar usuário pelo CPF")
    void testDeleteByCpf() {
        Receptionist r1 = new Receptionist("Usuario 1", "111.111.111-11", "senha1");
        Receptionist r2 = new Receptionist("Usuario 2", "222.222.222-22", "senha2");
        
        repository.save(r1);
        repository.save(r2);
        
        repository.deleteByCpf("111.111.111-11");
        
        List<Receptionist> remaining = repository.loadAll();
        assertEquals(1, remaining.size());
        assertEquals("Usuario 2", remaining.get(0).getName());
        assertNull(repository.findByCPF("111.111.111-11"));
    }

    @Test
    @DisplayName("Deve adicionar múltiplos elementos e manter a ordem")
    void testMultipleSaves() {
        Receptionist r1 = new Receptionist("Usuario 1", "111.111.111-11", "senha1");
        Receptionist r2 = new Receptionist("Usuario 2", "222.222.222-22", "senha2");
        Receptionist r3 = new Receptionist("Usuario 3", "333.333.333-33", "senha3");
        
        repository.save(r1);
        repository.save(r2);
        repository.save(r3);
        
        List<Receptionist> loaded = repository.loadAll();
        assertEquals(3, loaded.size());
    }
}
