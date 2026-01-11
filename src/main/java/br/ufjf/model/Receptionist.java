package br.ufjf.model;

import java.util.UUID;

public class Receptionist extends User {
    public Receptionist(){ super(); };

    public Receptionist(String name, String cpf, String password){ super(name, cpf, password); }

    public String getDashboardFxml() {
        return "DashboardReceptionist.fxml";
    }
}
