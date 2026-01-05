package br.ufjf.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Consulta{
    private String id;
    private String data;
    private String hora;
    private Pacient paciente;
    private Medico medico;

    public Consulta(LocalDate data, LocalTime hora, Pacient paciente, Medico medico) {
        this.id = UUID.randomUUID().toString();
        this.data = data.toString();
        this.hora = hora.toString();
        this.paciente = paciente;
        this.medico = medico;
    }

    public String getId(){ return this.id;}
    public LocalDate getData() { return LocalDate.parse(data); }
    public LocalTime getHora() { return LocalTime.parse(hora); }
    public Pacient getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }

    @Override
    public String toString() {
        return getData() + " | " + getPaciente().getName();
    }
}
