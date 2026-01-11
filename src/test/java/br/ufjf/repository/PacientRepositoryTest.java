package br.ufjf.repository;

import br.ufjf.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PacientRepositoryTest {

    private PacientRepository repository;
    private ConsultaRepository consultaRepository;

    @BeforeEach
    void setUp() {
        repository = new PacientRepository();
        consultaRepository = new ConsultaRepository();

        repository.saveAll(new ArrayList<>());
        consultaRepository.saveAll(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve adicionar documento ao paciente")
    void testUpdateDocumentos() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        repository.save(paciente);
        
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico, "Consulta teste", StatusConsulta.REALIZADA);
        consultaRepository.save(consulta);
        
        Documento documento = new Documento(consulta, TipoDocumento.ATESTADO, "Atestado médico", 3);
        
        repository.updateDocumentos("111.111.111-11", documento);
        
        Pacient updated = repository.findByCPF("111.111.111-11");
        assertNotNull(updated);
        assertNotNull(updated.getDocumentos());
        assertEquals(1, updated.getDocumentos().size());
        assertEquals(TipoDocumento.ATESTADO, updated.getDocumentos().get(0).getTipoDocumento());
    }

    @Test
    @DisplayName("Deve criar lista de documentos se paciente não tiver documentos")
    void testUpdateDocumentosWhenDocumentosIsNull() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        repository.save(paciente);
        
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        consultaRepository.save(consulta);
        
        Documento documento = new Documento(consulta, TipoDocumento.RECEITA, "Receita médica");
        
        repository.updateDocumentos("111.111.111-11", documento);
        
        Pacient pacienteUpdated = repository.findByCPF("111.111.111-11");
        assertNotNull(pacienteUpdated.getDocumentos());
        assertEquals(1, pacienteUpdated.getDocumentos().size());
    }

    @Test
    @DisplayName("Não deve adicionar documento duplicado")
    void testUpdateDocumentosDoesNotAddDuplicate() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        repository.save(paciente);
        
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        consultaRepository.save(consulta);
        
        Documento documento = new Documento(consulta, TipoDocumento.ATESTADO, "Atestado médico", 3);

        repository.updateDocumentos("111.111.111-11", documento);
        repository.updateDocumentos("111.111.111-11", documento);
        
        Pacient pacienteUpdated = repository.findByCPF("111.111.111-11");
        assertEquals(1, pacienteUpdated.getDocumentos().size());
    }

    @Test
    @DisplayName("Deve verificar se documento já existe")
    void testIsExists() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        repository.save(paciente);
        
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        consultaRepository.save(consulta);
        
        Documento documento1 = new Documento(consulta, TipoDocumento.ATESTADO, "Atestado", 3);
        Documento documento2 = new Documento(consulta, TipoDocumento.RECEITA, "Receita");
        Documento documento3 = new Documento(consulta, TipoDocumento.ATESTADO, "Atestado 2", 5);
        
        repository.updateDocumentos("111.111.111-11", documento1);
        repository.updateDocumentos("111.111.111-11", documento2);
        
        Pacient pacienteUpdated = repository.findByCPF("111.111.111-11");

        assertTrue(repository.isExists(documento1, pacienteUpdated));

        assertTrue(repository.isExists(documento2, pacienteUpdated));

        assertTrue(repository.isExists(documento3, pacienteUpdated));
    }

    @Test
    @DisplayName("Deve retornar false quando documento não existe")
    void testIsExistsReturnsFalse() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        repository.save(paciente);
        
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta1 = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        Consulta consulta2 = new Consulta(LocalDate.now().plusDays(1), LocalTime.of(10, 0), paciente, medico);
        consultaRepository.save(consulta1);
        consultaRepository.save(consulta2);
        
        Documento documento1 = new Documento(consulta1, TipoDocumento.ATESTADO, "Atestado", 3);
        Documento documento2 = new Documento(consulta2, TipoDocumento.ATESTADO, "Atestado", 3);
        
        repository.updateDocumentos("111.111.111-11", documento1);
        
        Pacient pacienteUpdated = repository.findByCPF("111.111.111-11");

        assertFalse(repository.isExists(documento2, pacienteUpdated));
    }

    @Test
    @DisplayName("Deve atualizar status de internação do paciente")
    void testUpdateStatusInternacao() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        repository.save(paciente);
        
        repository.updateStatusInternacao("111.111.111-11", StatusInternacao.APTO);
        
        Pacient updated = repository.findByCPF("111.111.111-11");
        assertNotNull(updated);
        assertEquals(StatusInternacao.APTO, updated.getStatusInternacao());
    }

    @Test
    @DisplayName("Deve atualizar status de internação apenas do paciente com CPF especificado")
    void testUpdateStatusInternacaoOnlyTargetPacient() {
        Pacient paciente1 = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Pacient paciente2 = new Pacient("Maria Santos", "222.222.222-22", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        
        repository.save(paciente1);
        repository.save(paciente2);
        
        repository.updateStatusInternacao("111.111.111-11", StatusInternacao.APTO);
        
        Pacient pacienteUpdated1 = repository.findByCPF("111.111.111-11");
        Pacient pacienteUpdated2 = repository.findByCPF("222.222.222-22");
        
        assertEquals(StatusInternacao.APTO, pacienteUpdated1.getStatusInternacao());
        assertEquals(StatusInternacao.NAO_INTERNADO, pacienteUpdated2.getStatusInternacao());
    }
}
