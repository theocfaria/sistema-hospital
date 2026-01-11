package br.ufjf.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultaTest {

    @Test
    @DisplayName("Deve criar consulta com construtor básico")
    void testConstrutorBasico() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        LocalDate data = LocalDate.now();
        LocalTime hora = LocalTime.of(10, 0);
        
        Consulta consulta = new Consulta(data, hora, paciente, medico);
        
        assertNotNull(consulta);
        assertNotNull(consulta.getId());
        assertEquals(data, consulta.getData());
        assertEquals(hora, consulta.getHora());
        assertEquals(paciente, consulta.getPaciente());
        assertEquals(medico, consulta.getMedico());
    }

    @Test
    @DisplayName("Deve criar consulta com construtor completo")
    void testConstrutorCompleto() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        LocalDate data = LocalDate.now();
        LocalTime hora = LocalTime.of(10, 0);
        String descricao = "Consulta";
        
        Consulta consulta = new Consulta(data, hora, paciente, medico, descricao, StatusConsulta.AGENDADA);
        
        assertNotNull(consulta);
        assertEquals(data, consulta.getData());
        assertEquals(hora, consulta.getHora());
        assertEquals(descricao, consulta.getDescricaoClinica());
        assertEquals(StatusConsulta.AGENDADA, consulta.getStatusConsulta());
    }

    @Test
    @DisplayName("Deve gerar ID único para cada consulta")
    void testIdUnico() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        LocalDate data = LocalDate.now();
        LocalTime hora = LocalTime.of(10, 30);
        
        Consulta consulta1 = new Consulta(data, hora, paciente, medico);
        Consulta consulta2 = new Consulta(data, hora, paciente, medico);
        
        assertNotEquals(consulta1.getId(), consulta2.getId());
    }

    @Test
    @DisplayName("Deve obter e definir descrição clínica")
    void testDescricaoClinica() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        
        consulta.setDescricaoClinica("Nova descrição");
        assertEquals("Nova descrição", consulta.getDescricaoClinica());
    }

    @Test
    @DisplayName("Deve obter e definir status da consulta")
    void testStatusConsulta() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        
        consulta.setStatusConsulta(StatusConsulta.REALIZADA);
        assertEquals(StatusConsulta.REALIZADA, consulta.getStatusConsulta());
        
        consulta.setStatusConsulta(StatusConsulta.CANCELADA);
        assertEquals(StatusConsulta.CANCELADA, consulta.getStatusConsulta());
    }

    @Test
    @DisplayName("Deve retornar nome do paciente")
    void testGetNomePaciente() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        
        assertEquals("João Silva", consulta.getNomePaciente());
    }

    @Test
    @DisplayName("Deve retornar nome do médico")
    void testGetNomeMedico() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        
        assertEquals("Dr. Maria", consulta.getNomeMedico());
    }

    @Test
    @DisplayName("Deve retornar representação textual correta")
    void testToString() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        LocalDate data = LocalDate.of(2024, 1, 15);
        Consulta consulta = new Consulta(data, LocalTime.of(10, 0), paciente, medico);
        
        String resultado = consulta.toString();
        assertTrue(resultado.contains(data.toString()));
        assertTrue(resultado.contains("João Silva"));
    }
}
