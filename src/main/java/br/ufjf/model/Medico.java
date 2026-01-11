package br.ufjf.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class Medico extends User {
    private List<DayOfWeek> diasAtendimento;
    private String inicio;
    private String fim;
    private StatusAtendimento statusAtendimento;
    private int duracao;

    public Medico() {
        super();
    }

    public Medico(String name, String cpf, String password){
        super(name, cpf, password);
    }

    public Medico(String name, String cpf, String password, List<DayOfWeek> diasAtendimento, String inicio, String fim, int duracao, StatusAtendimento statusAtendimento) {
        super(name, cpf, password);
        this.diasAtendimento = diasAtendimento;
        this.inicio = inicio;
        this.fim = fim;
        this.duracao = duracao;
        this.statusAtendimento = statusAtendimento;
    }

    public Medico(String name, String cpf, String password, List<DayOfWeek> diasAtendimento, String inicio, String fim, int duracao) {
        super(name, cpf, password);
        this.diasAtendimento = diasAtendimento;
        this.inicio = inicio;
        this.fim = fim;
        this.duracao = duracao;
    }

    public String getDashboardFxml() {
        return "DashboardMedico.fxml";
    }
    public void setDiasAtendimento(List<DayOfWeek> diasAtendimento) {
        this.diasAtendimento = diasAtendimento;
    }
    public List<DayOfWeek> getDiasAtendimento() {
        return this.diasAtendimento;
    }

    public LocalTime getInicio() { return LocalTime.parse(this.inicio); }

    public StatusAtendimento getStatusAtendimento() { return this.statusAtendimento; }

    public void setStatusAtendimento(StatusAtendimento statusAtendimento) { this.statusAtendimento = statusAtendimento; }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio.toString();
    }

    public LocalTime getFim() { return LocalTime.parse(this.fim); }

    public void setFim(LocalTime fim) {
        this.fim = fim.toString();
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }
}