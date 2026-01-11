package br.ufjf.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MedicoTest {

    @Test
    @DisplayName("Deve criar médico com construtor padrão")
    void testConstrutorPadrao() {
        Medico medico = new Medico();
        assertNotNull(medico);
    }

    @Test
    @DisplayName("Deve criar médico com construtor completo")
    void testConstrutorCompleto() {
        List<DayOfWeek> dias = List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
        Medico medico = new Medico("Dr. João", "111.111.111-11", "senha", dias, "08:00", "12:00", "30");
        
        assertNotNull(medico);
        assertEquals("Dr. João", medico.getName());
        assertEquals("111.111.111-11", medico.getCpf());
        assertEquals(dias, medico.getDiasAtendimento());
    }

    @Test
    @DisplayName("Deve retornar DashboardMedico.fxml")
    void testGetDashboardFxml() {
        Medico medico = new Medico();
        assertEquals("DashboardMedico.fxml", medico.getDashboardFxml());
    }

    @Test
    @DisplayName("Deve definir e obter dias de atendimento")
    void testDiasAtendimento() {
        Medico medico = new Medico();
        List<DayOfWeek> dias = List.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
        
        medico.setDiasAtendimento(dias);
        assertEquals(dias, medico.getDiasAtendimento());
    }

    @Test
    @DisplayName("Deve definir e obter horário de início")
    void testInicio() {
        Medico medico = new Medico();
        LocalTime inicio = LocalTime.of(8, 0);
        
        medico.setInicio(inicio);
        assertEquals(inicio, medico.getInicio());
    }

    @Test
    @DisplayName("Deve definir e obter horário de fim")
    void testFim() {
        Medico medico = new Medico();
        LocalTime fim = LocalTime.of(18, 0);
        
        medico.setFim(fim);
        assertEquals(fim, medico.getFim());
    }

    @Test
    @DisplayName("Deve definir e obter duração")
    void testDuracao() {
        Medico medico = new Medico();
        medico.setDuracao(Integer.toString(30));
        assertEquals(Integer.toString(30), medico.getDuracao());
    }

    @Test
    @DisplayName("Deve converter LocalTime para String e vice-versa corretamente")
    void testConversaoLocalTime() {
        Medico medico = new Medico();
        LocalTime original = LocalTime.of(14, 30);
        
        medico.setInicio(original);
        LocalTime recuperado = medico.getInicio();
        
        assertEquals(original, recuperado);
    }
}
