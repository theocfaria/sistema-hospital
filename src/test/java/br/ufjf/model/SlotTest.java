package br.ufjf.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;


public class SlotTest {

    @Test
    @DisplayName("Deve criar slot sem consulta associada")
    void testConstrutorSemConsulta() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        LocalDate data = LocalDate.now();
        LocalTime horario = LocalTime.of(10, 0);
        
        Slot slot = new Slot(data, horario, paciente);
        
        assertNotNull(slot);
        assertEquals(data, slot.getData());
        assertEquals(horario, slot.getHorario());
        assertEquals(paciente, slot.getPaciente());
        assertNull(slot.getConsulta());
    }

    @Test
    @DisplayName("Deve criar slot com consulta associada")
    void testConstrutorComConsulta() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        Medico medico = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        LocalDate data = LocalDate.now();
        LocalTime horario = LocalTime.of(10, 0);
        Consulta consulta = new Consulta(data, horario, paciente, medico);
        
        Slot slot = new Slot(data, horario, paciente, consulta);
        
        assertNotNull(slot);
        assertEquals(consulta, slot.getConsulta());
    }

    @Test
    @DisplayName("Deve retornar 'LIVRE' quando paciente é null")
    void testGetStatusPacienteLivre() {
        LocalDate data = LocalDate.now();
        LocalTime horario = LocalTime.of(10, 0);
        
        Slot slot = new Slot(data, horario, null);
        
        assertEquals("LIVRE", slot.getStatusPaciente());
    }

    @Test
    @DisplayName("Deve retornar nome do paciente quando paciente não é null")
    void testGetStatusPacienteOcupado() {
        Pacient paciente = new Pacient("João Silva", "111.111.111-11", "senha", null, StatusInternacao.NAO_INTERNADO);
        LocalDate data = LocalDate.now();
        LocalTime horario = LocalTime.of(10, 0);
        
        Slot slot = new Slot(data, horario, paciente);
        
        assertEquals("João Silva", slot.getStatusPaciente());
    }
}
