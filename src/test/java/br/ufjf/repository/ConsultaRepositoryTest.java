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

public class ConsultaRepositoryTest {

    private ConsultaRepository repository;
    private MedicoRepository medicoRepository;
    private PacientRepository pacientRepository;

    @BeforeEach
    void setUp() {
        repository = new ConsultaRepository();
        medicoRepository = new MedicoRepository();
        pacientRepository = new PacientRepository();

        repository.saveAll(new ArrayList<>());
        medicoRepository.saveAll(new ArrayList<>());
        pacientRepository.saveAll(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve salvar uma nova consulta")
    void testSave() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico, "Descrição", StatusConsulta.AGENDADA);
        
        repository.save(consulta);
        
        List<Consulta> consultas = repository.loadAll();
        assertEquals(1, consultas.size());
        assertEquals("João Silva", consultas.get(0).getPaciente().getName());
    }

    @Test
    @DisplayName("Deve encontrar consulta por médico, data e hora")
    void testFindConsulta() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        
        LocalDate data = LocalDate.of(2024, 1, 15);
        LocalTime hora = LocalTime.of(10, 0);
        Consulta consulta = new Consulta(data, hora, paciente, medico, "Descrição", StatusConsulta.AGENDADA);
        repository.save(consulta);
        
        Consulta found = repository.findConsulta(medico, data, hora);
        
        assertNotNull(found);
        assertEquals(consulta.getId(), found.getId());
        assertEquals(data, found.getData());
        assertEquals(hora, found.getHora());
    }

    @Test
    @DisplayName("Deve retornar null quando consulta não existe")
    void testFindConsultaNotFound() {
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        LocalDate data = LocalDate.of(2024, 1, 15);
        LocalTime hora = LocalTime.of(10, 0);
        
        Consulta found = repository.findConsulta(medico, data, hora);
        assertNull(found);
    }

    @Test
    @DisplayName("Deve encontrar todas as consultas de um médico")
    void testFindByMedico() {
        Pacient paciente1 = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Pacient paciente2 = new Pacient("Maria Santos", "333.333.333-33", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Medico medico1 = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Medico medico2 = new Medico("Dr. João", "444.444.444-44", "senha", null, null, null, null);
        
        Consulta consulta1 = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente1, medico1);
        Consulta consulta2 = new Consulta(LocalDate.now(), LocalTime.of(11, 0), paciente2, medico1);
        Consulta consulta3 = new Consulta(LocalDate.now(), LocalTime.of(14, 0), paciente1, medico2);
        
        repository.save(consulta1);
        repository.save(consulta2);
        repository.save(consulta3);
        
        List<Consulta> consultasMedico1 = repository.findByMedico(medico1);
        
        assertEquals(2, consultasMedico1.size());
        assertTrue(consultasMedico1.stream().allMatch(c -> c.getMedico().getCpf().equals(medico1.getCpf())));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando médico não tem consultas")
    void testFindByMedicoEmpty() {
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        
        List<Consulta> consultas = repository.findByMedico(medico);
        assertTrue(consultas.isEmpty());
    }

    @Test
    @DisplayName("Deve encontrar pacientes únicos atendidos por um médico")
    void testFindPacientesbyMedico() {
        Pacient paciente1 = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Pacient paciente2 = new Pacient("Maria Santos", "333.333.333-33", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);

        Consulta consulta1 = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente1, medico);
        Consulta consulta2 = new Consulta(LocalDate.now().plusDays(1), LocalTime.of(10, 0), paciente1, medico);
        Consulta consulta3 = new Consulta(LocalDate.now(), LocalTime.of(11, 0), paciente2, medico);
        
        repository.save(consulta1);
        repository.save(consulta2);
        repository.save(consulta3);
        
        List<Pacient> pacientes = repository.findPacientesbyMedico(medico);
        
        assertEquals(2, pacientes.size());
        assertTrue(pacientes.stream().anyMatch(p -> p.getCpf().equals(paciente1.getCpf())));
        assertTrue(pacientes.stream().anyMatch(p -> p.getCpf().equals(paciente2.getCpf())));
    }

    @Test
    @DisplayName("Deve encontrar todas as consultas de um paciente")
    void testFindConsultabyPaciente() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Medico medico1 = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Medico medico2 = new Medico("Dr. João", "444.444.444-44", "senha", null, null, null, null);
        
        Consulta consulta1 = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico1);
        Consulta consulta2 = new Consulta(LocalDate.now().plusDays(1), LocalTime.of(10, 0), paciente, medico1);
        Consulta consulta3 = new Consulta(LocalDate.now(), LocalTime.of(14, 0), paciente, medico2);
        
        repository.save(consulta1);
        repository.save(consulta2);
        repository.save(consulta3);
        
        List<Consulta> consultas = repository.findConsultabyPaciente("111.111.111-11");
        
        assertEquals(3, consultas.size());
        assertTrue(consultas.stream().allMatch(c -> c.getPaciente().getCpf().equals(paciente.getCpf())));
    }

    @Test
    @DisplayName("Deve atualizar status da consulta")
    void testUpdateStatus() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico, null, StatusConsulta.AGENDADA);
        repository.save(consulta);
        
        repository.updateStatus(consulta, StatusConsulta.REALIZADA);
        
        Consulta updated = repository.findConsulta(medico, consulta.getData(), consulta.getHora());
        assertNotNull(updated);
        assertEquals(StatusConsulta.REALIZADA, updated.getStatusConsulta());
    }

    @Test
    @DisplayName("Deve atualizar descrição clínica da consulta")
    void testUpdateDescricao() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico, null, StatusConsulta.AGENDADA);
        repository.save(consulta);
        
        String novaDescricao = "Paciente em ótimo estado de saúde";
        repository.updateDescricao(consulta, novaDescricao);
        
        Consulta updated = repository.findConsulta(medico, consulta.getData(), consulta.getHora());
        assertNotNull(updated);
        assertEquals(novaDescricao, updated.getDescricaoClinica());
    }
}
