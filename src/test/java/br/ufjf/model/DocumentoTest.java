package br.ufjf.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentoTest {

    @Test
    @DisplayName("Deve criar atestado")
    void testConstrutorComDiasAfastamento() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        
        Documento documento = new Documento(consulta, TipoDocumento.ATESTADO, "Atestado médico", 3);
        
        assertNotNull(documento);
        assertEquals(consulta, documento.getConsulta());
        assertEquals(TipoDocumento.ATESTADO, documento.getTipoDocumento());
        assertEquals("Atestado médico", documento.getInformacao());
        assertEquals(3, documento.getDiasAfastamento());
    }

    @Test
    @DisplayName("Deve criar receita")
    void testConstrutorSemDiasAfastamento() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        
        Documento documento = new Documento(consulta, TipoDocumento.RECEITA, "Receita médica");
        
        assertNotNull(documento);
        assertEquals(TipoDocumento.RECEITA, documento.getTipoDocumento());
        assertEquals("Receita médica", documento.getInformacao());
        assertEquals(0, documento.getDiasAfastamento());
    }

    @Test
    @DisplayName("Deve obter e definir consulta")
    void testConsulta() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta1 = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        Consulta consulta2 = new Consulta(LocalDate.now().plusDays(1), LocalTime.of(10, 0), paciente, medico);
        
        Documento documento = new Documento(consulta1, TipoDocumento.ATESTADO, "Atestado", 3);
        documento.setConsulta(consulta2);
        
        assertEquals(consulta2, documento.getConsulta());
    }

    @Test
    @DisplayName("Deve obter e definir tipo de documento")
    void testTipoDocumento() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        
        Documento documento = new Documento(consulta, TipoDocumento.ATESTADO, "Atestado", 3);
        documento.setTipoDocumento(TipoDocumento.RECEITA);
        
        assertEquals(TipoDocumento.RECEITA, documento.getTipoDocumento());
    }

    @Test
    @DisplayName("Deve obter e definir informação")
    void testInformacao() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        
        Documento documento = new Documento(consulta, TipoDocumento.ATESTADO, "Atestado original", 3);
        documento.setInformacao("Nova informação");
        
        assertEquals("Nova informação", documento.getInformacao());
    }

    @Test
    @DisplayName("Deve obter e definir dias de afastamento")
    void testDiasAfastamento() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        
        Documento documento = new Documento(consulta, TipoDocumento.ATESTADO, "Atestado", 3);
        documento.setDiasAfastamento(5);
        
        assertEquals(5, documento.getDiasAfastamento());
    }

    @Test
    @DisplayName("Deve retornar data da consulta como String")
    void testGetData() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        LocalDate data = LocalDate.of(2024, 1, 15);
        Consulta consulta = new Consulta(data, LocalTime.of(10, 0), paciente, medico);
        
        Documento documento = new Documento(consulta, TipoDocumento.ATESTADO, "Atestado", 3);
        
        assertEquals(data.toString(), documento.getData());
    }

    @Test
    @DisplayName("Deve retornar nome do médico da consulta")
    void testGetMedico() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        
        Documento documento = new Documento(consulta, TipoDocumento.ATESTADO, "Atestado", 3);
        
        assertEquals("Dr. Maria", documento.getMedico());
    }

    @Test
    @DisplayName("Deve retornar tipo como String")
    void testGetTipo() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        
        Documento documento = new Documento(consulta, TipoDocumento.ATESTADO, "Atestado", 3);
        
        assertEquals("ATESTADO", documento.getTipo());
    }

    @Test
    @DisplayName("Deve retornar descrição")
    void testGetDescricao() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta = new Consulta(LocalDate.now(), LocalTime.of(10, 0), paciente, medico);
        
        Documento documento = new Documento(consulta, TipoDocumento.ATESTADO, "Descrição do atestado", 3);
        
        assertEquals("Descrição do atestado", documento.getDescricao());
    }
}
