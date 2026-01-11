package br.ufjf.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PacientTest {

    @Test
    @DisplayName("Deve criar paciente com construtor padrão")
    void testConstrutorPadrao() {
        Pacient paciente = new Pacient();
        assertNotNull(paciente);
    }

    @Test
    @DisplayName("Deve criar paciente com construtor completo")
    void testConstrutorCompleto() {
        List<Documento> documentos = new ArrayList<>();
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", documentos, StatusInternacao.NAO_INTERNADO);
        
        assertNotNull(paciente);
        assertEquals("João Silva", paciente.getName());
        assertEquals("111.111.111-11", paciente.getCpf());
        assertEquals("senha", paciente.getPassword());
        assertEquals(StatusInternacao.NAO_INTERNADO, paciente.getStatusInternacao());
        assertNotNull(paciente.getDocumentos());
    }

    @Test
    @DisplayName("Deve criar lista vazia quando documentos é null")
    void testConstrutorComDocumentosNull() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        assertNotNull(paciente.getDocumentos());
        assertTrue(paciente.getDocumentos().isEmpty());
    }

    @Test
    @DisplayName("Deve retornar lista de atestados")
    void testGetAtestados() {
        List<Documento> documentos = new ArrayList<>();
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", documentos, StatusInternacao.NAO_INTERNADO);
        
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta1 = new Consulta(java.time.LocalDate.now(), java.time.LocalTime.of(10, 0), paciente, medico);
        Consulta consulta2 = new Consulta(java.time.LocalDate.now(), java.time.LocalTime.of(11, 0), paciente, medico);
        
        Documento atestado = new Documento(consulta1, TipoDocumento.ATESTADO, "Atestado", 3);
        Documento receita = new Documento(consulta2, TipoDocumento.RECEITA, "Receita");
        
        paciente.getDocumentos().add(atestado);
        paciente.getDocumentos().add(receita);
        
        List<Documento> atestados = paciente.getAtestados();
        assertEquals(1, atestados.size());
        assertEquals(TipoDocumento.ATESTADO, atestados.get(0).getTipoDocumento());
    }

    @Test
    @DisplayName("Deve retornar lista de receitas")
    void testGetReceitas() {
        List<Documento> documentos = new ArrayList<>();
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", documentos, StatusInternacao.NAO_INTERNADO);
        
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        Consulta consulta1 = new Consulta(java.time.LocalDate.now(), java.time.LocalTime.of(10, 0), paciente, medico);
        Consulta consulta2 = new Consulta(java.time.LocalDate.now(), java.time.LocalTime.of(11, 0), paciente, medico);
        
        Documento atestado = new Documento(consulta1, TipoDocumento.ATESTADO, "Atestado", 3);
        Documento receita = new Documento(consulta2, TipoDocumento.RECEITA, "Receita");
        
        paciente.getDocumentos().add(atestado);
        paciente.getDocumentos().add(receita);
        
        List<Documento> receitas = paciente.getReceitas();
        assertEquals(1, receitas.size());
        assertEquals(TipoDocumento.RECEITA, receitas.get(0).getTipoDocumento());
    }

    @Test
    @DisplayName("Deve definir e obter documentos")
    void testSetDocumentos() {
        Pacient paciente = new Pacient();
        List<Documento> documentos = new ArrayList<>();
        
        paciente.setDocumentos(documentos);
        assertEquals(documentos, paciente.getDocumentos());
    }

    @Test
    @DisplayName("Deve criar lista vazia quando setDocumentos recebe null")
    void testSetDocumentosNull() {
        Pacient paciente = new Pacient();
        paciente.setDocumentos(null);
        assertNotNull(paciente.getDocumentos());
        assertTrue(paciente.getDocumentos().isEmpty());
    }

    @Test
    @DisplayName("Deve definir e obter status de internação")
    void testStatusInternacao() {
        Pacient paciente = new Pacient();
        paciente.setStatusInternacao(StatusInternacao.APTO);
        assertEquals(StatusInternacao.APTO, paciente.getStatusInternacao());
        
        paciente.setStatusInternacao(StatusInternacao.NAO_APTO);
        assertEquals(StatusInternacao.NAO_APTO, paciente.getStatusInternacao());
    }

    @Test
    @DisplayName("Deve definir e obter email")
    void testEmail() {
        Pacient paciente = new Pacient();
        paciente.setEmail("joao@email.com");
        assertEquals("joao@email.com", paciente.getEmail());
    }

    @Test
    @DisplayName("Deve definir e obter telefone")
    void testTelefone() {
        Pacient paciente = new Pacient();
        paciente.setTelefone("(32) 99999-9999");
        assertEquals("(32) 99999-9999", paciente.getTelefone());
    }

    @Test
    @DisplayName("Deve definir e obter endereço")
    void testEndereco() {
        Pacient paciente = new Pacient();
        paciente.setEndereco("Rua das Flores, 123");
        assertEquals("Rua das Flores, 123", paciente.getEndereco());
    }

    @Test
    @DisplayName("Deve retornar DashboardPaciente.fxml")
    void testGetDashboardFxml() {
        Pacient paciente = new Pacient();
        assertEquals("DashboardPaciente.fxml", paciente.getDashboardFxml());
    }

    @Test
    @DisplayName("Deve retornar nome no toString()")
    void testToString() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", new ArrayList<>(), StatusInternacao.NAO_INTERNADO);
        assertEquals("João Silva", paciente.toString());
    }
}
