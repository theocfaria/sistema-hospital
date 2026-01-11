package br.ufjf.repository;

import br.ufjf.model.Medico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MedicoRepositoryTest {

    private MedicoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MedicoRepository();

        repository.saveAll(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve atualizar horários de atendimento do médico pelo CPF")
    void testUpdateHorarios() {
        Medico medico = new Medico("Dr. João", "111.111.111-11", "senha", null, null, null, null);
        repository.save(medico);
        
        List<DayOfWeek> dias = List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        LocalTime inicio = LocalTime.of(8, 0);
        LocalTime fim = LocalTime.of(12, 0);
        String duracao = Integer.toString(60);
        
        repository.updateHorarios("111.111.111-11", dias, inicio, fim, duracao);
        
        Medico medicoUpdated = repository.findByCPF("111.111.111-11");
        assertNotNull(medicoUpdated);
        assertEquals(dias, medicoUpdated.getDiasAtendimento());
        assertEquals(inicio, medicoUpdated.getInicio());
        assertEquals(fim, medicoUpdated.getFim());
        assertEquals(duracao, medicoUpdated.getDuracao());
    }

    @Test
    @DisplayName("Deve atualizar horários apenas do médico com CPF especificado")
    void testUpdateHorariosOnlyTargetMedico() {
        Medico medico1 = new Medico("Dr. João", "111.111.111-11", "senha", null, null, null, null);
        Medico medico2 = new Medico("Dr. Maria", "222.222.222-22", "senha", null, null, null, null);
        
        repository.save(medico1);
        repository.save(medico2);
        
        List<DayOfWeek> dias = List.of(DayOfWeek.MONDAY);
        LocalTime inicio = LocalTime.of(9, 0);
        LocalTime fim = LocalTime.of(11, 0);
        String duracao = Integer.toString(60);
        
        repository.updateHorarios("111.111.111-11", dias, inicio, fim, duracao);
        
        Medico medicoUpdated1 = repository.findByCPF("111.111.111-11");
        Medico medicoUpdated2 = repository.findByCPF("222.222.222-22");
        
        assertNotNull(medicoUpdated1);
        assertEquals(dias, medicoUpdated1.getDiasAtendimento());
        assertEquals(inicio, medicoUpdated1.getInicio());
        assertEquals(fim, medicoUpdated1.getFim());

        assertNotNull(medicoUpdated2);
        assertEquals("Dr. Maria", medicoUpdated2.getName());
    }

    @Test
    @DisplayName("Não deve alterar nada quando CPF não existe")
    void testUpdateHorariosWithNonExistentCPF() {
        Medico medico = new Medico("Dr. João", "111.111.111-11", "senha", null, null, null, null);
        repository.save(medico);
        
        List<DayOfWeek> dias = List.of(DayOfWeek.MONDAY);
        LocalTime inicio = LocalTime.of(9, 0);
        LocalTime fim = LocalTime.of(11, 0);
        String duracao = Integer.toString(60);

        repository.updateHorarios("999.999.999-99", dias, inicio, fim, duracao);

        Medico naoAlterado = repository.findByCPF("111.111.111-11");
        assertNotNull(naoAlterado);
        assertEquals("Dr. João", naoAlterado.getName());
    }
}
