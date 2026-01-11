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

public class DocumentoRepositoryTest {

    private DocumentoRepository repository;
    private ConsultaRepository consultaRepository;
    private MedicoRepository medicoRepository;
    private PacientRepository pacientRepository;

    @BeforeEach
    void setUp() {
        repository = new DocumentoRepository();
        consultaRepository = new ConsultaRepository();
        medicoRepository = new MedicoRepository();
        pacientRepository = new PacientRepository();

        repository.saveAll(new ArrayList<>());
        consultaRepository.saveAll(new ArrayList<>());
        medicoRepository.saveAll(new ArrayList<>());
        pacientRepository.saveAll(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve salvar um novo documento")
    void testSave() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico, "Descrição", StatusConsulta.REALIZADA);
        consultaRepository.save(consulta);
        
        Documento documento = new Documento(consulta, TipoDocumento.ATESTADO, "Atestado médico", 3);
        repository.save(documento);
        
        List<Documento> documentos = repository.loadAll();
        assertEquals(1, documentos.size());
        assertEquals(TipoDocumento.ATESTADO, documentos.get(0).getTipoDocumento());
        assertEquals(3, documentos.get(0).getDiasAfastamento());
    }

    @Test
    @DisplayName("Deve salvar receita sem dias de afastamento")
    void testSaveReceita() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        consultaRepository.save(consulta);
        
        Documento receita = new Documento(consulta, TipoDocumento.RECEITA, "Receita médica");
        repository.save(receita);
        
        List<Documento> documentos = repository.loadAll();
        assertEquals(1, documentos.size());
        assertEquals(TipoDocumento.RECEITA, documentos.get(0).getTipoDocumento());
    }

    @Test
    @DisplayName("Deve encontrar documento pelo CPF do paciente")
    void testFindByCPF() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        consultaRepository.save(consulta);
        
        Documento documento = new Documento(consulta, TipoDocumento.ATESTADO, "Atestado", 3);
        repository.save(documento);
        
        Documento foundDocumento = repository.findByCPF("111.111.111-11");
        
        assertNotNull(foundDocumento);
        assertEquals(TipoDocumento.ATESTADO, foundDocumento.getTipoDocumento());
        assertEquals("111.111.111-11", foundDocumento.getConsulta().getPaciente().getCpf());
    }

    @Test
    @DisplayName("Deve retornar null quando CPF não existe")
    void testFindByCPFNotFound() {
        Documento found = repository.findByCPF("999.999.999-99");
        assertNull(found);
    }

    @Test
    @DisplayName("Deve encontrar documento por consulta e tipo")
    void testFindDocumento() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        consultaRepository.save(consulta);
        
        Documento atestado = new Documento(consulta, TipoDocumento.ATESTADO, "Atestado", 3);
        Documento receita = new Documento(consulta, TipoDocumento.RECEITA, "Receita");
        
        repository.save(atestado);
        repository.save(receita);
        
        Documento foundAtestado = repository.findDocumento(consulta, TipoDocumento.ATESTADO);
        Documento foundReceita = repository.findDocumento(consulta, TipoDocumento.RECEITA);
        
        assertNotNull(foundAtestado);
        assertEquals(TipoDocumento.ATESTADO, foundAtestado.getTipoDocumento());
        assertEquals(consulta.getId(), foundAtestado.getConsulta().getId());
        
        assertNotNull(foundReceita);
        assertEquals(TipoDocumento.RECEITA, foundReceita.getTipoDocumento());
        assertEquals(consulta.getId(), foundReceita.getConsulta().getId());
    }

    @Test
    @DisplayName("Deve retornar null quando documento não existe para consulta e tipo")
    void testFindDocumentoNotFound() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        consultaRepository.save(consulta);
        
        Documento found = repository.findDocumento(consulta, TipoDocumento.ATESTADO);
        assertNull(found);
    }

    @Test
    @DisplayName("Deve salvar múltiplos documentos")
    void testSaveMultipleDocuments() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        
        Consulta consulta1 = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        Consulta consulta2 = new Consulta(LocalDate.now().plusDays(1), LocalTime.of(10, 0), paciente, medico);
        consultaRepository.save(consulta1);
        consultaRepository.save(consulta2);
        
        Documento documento1 = new Documento(consulta1, TipoDocumento.ATESTADO, "Atestado 1", 3);
        Documento documento2 = new Documento(consulta2, TipoDocumento.RECEITA, "Receita 1");
        
        repository.save(documento1);
        repository.save(documento2);
        
        List<Documento> documentos = repository.loadAll();
        assertEquals(2, documentos.size());
    }
}
