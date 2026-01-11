package br.ufjf.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Consulta {
    private String id;
    private String data;
    private String hora;
    private Pacient paciente;
    private Medico medico;
    private String descricaoClinica;
    private StatusConsulta statusConsulta;

    public Consulta(LocalDate data, LocalTime hora, Pacient paciente, Medico medico) {
        this.id = UUID.randomUUID().toString();
        this.data = data.toString();
        this.hora = hora.toString();
        this.paciente = paciente;
        this.medico = medico;
    }

    public Consulta(LocalDate data, LocalTime hora, Pacient paciente, Medico medico, String descricaoClinica,
            StatusConsulta statusConsulta) {
        this.id = UUID.randomUUID().toString();
        this.data = data.toString();
        this.hora = hora.toString();
        this.paciente = paciente;
        this.medico = medico;
        this.descricaoClinica = descricaoClinica;
        this.statusConsulta = statusConsulta;
    }

    public String getId() {
        return this.id;
    }

    public LocalDate getData() {
        return LocalDate.parse(data);
    }

    public LocalTime getHora() {
        return LocalTime.parse(hora);
    }

    public Pacient getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public String getDescricaoClinica() {
        return descricaoClinica;
    }

    public void setDescricaoClinica(String descricaoClinica) {
        this.descricaoClinica = descricaoClinica;
    }

    public StatusConsulta getStatusConsulta() {
        return statusConsulta;
    }

    public void setStatusConsulta(StatusConsulta statusConsulta) {
        this.statusConsulta = statusConsulta;
    }

    public String getNomePaciente() {
        return paciente.getName();
    }

    public String getNomeMedico() {
        return medico.getName();
    }

    public void setData(LocalDate data) {
        this.data = data.toString();
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora.toString();
    }

    @Override
    public String toString() {
        return getData() + " | " + getPaciente().getName();
    }
}
