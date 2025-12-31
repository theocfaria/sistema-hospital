package br.ufjf.model;

import java.time.DayOfWeek;
import java.util.List;

public class Medico extends User {
    private List<DayOfWeek> diasAtendimento;
    public Medico() {
        super();
    }

    public Medico(String name, String cpf, String password, List<DayOfWeek> diasAtendimento) {
        super(name, cpf, password);
        diasAtendimento = diasAtendimento;
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
}
