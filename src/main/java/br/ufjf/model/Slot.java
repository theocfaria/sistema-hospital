package br.ufjf.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Slot {
    private LocalDate data;
    private LocalTime horario;
    private Pacient paciente;

    public Slot(LocalDate data, LocalTime horario, Pacient paciente) {
        this.data = data;
        this.horario = horario;
        this.paciente = paciente;
    }

    public LocalDate getData() { return data; }
    public LocalTime getHorario() { return horario; }
    public Pacient getPaciente() { return paciente; }
    public String getStatusPaciente(){
        if(paciente==null){
            return "LIVRE";
        }
        return paciente.getName();
    }
}
