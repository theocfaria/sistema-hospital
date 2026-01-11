package br.ufjf.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Slot {
    private LocalDate data;
    private LocalTime horario;
    private Pacient paciente;
    private Consulta consulta;

    public Slot(LocalDate data, LocalTime horario, Pacient paciente) {
        this.data = data;
        this.horario = horario;
        this.paciente = paciente;
    }

    public Slot(LocalDate data, LocalTime horario, Pacient paciente, Consulta consulta) {
        this.data = data;
        this.horario = horario;
        this.paciente = paciente;
        this.consulta = consulta;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public Pacient getPaciente() {
        return paciente;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public String getStatusPaciente() {
        if (paciente == null) {
            return "LIVRE";
        }
        return paciente.getName();
    }

    public String getDiaSemanaFormatado(){
        DayOfWeek diaSemana = data.getDayOfWeek();

        if(diaSemana==DayOfWeek.MONDAY){
            return "SEGUNDA";
        }
        if(diaSemana==DayOfWeek.TUESDAY){
            return "TERÇA";
        }
        if(diaSemana==DayOfWeek.WEDNESDAY){
            return "QUARTA";
        }
        if(diaSemana==DayOfWeek.THURSDAY){
            return "QUINTA";
        }
        if(diaSemana==DayOfWeek.FRIDAY){
            return "SEXTA";
        }
        return "";
    }
}
