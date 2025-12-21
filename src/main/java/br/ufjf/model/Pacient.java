package br.ufjf.model;

public class Pacient extends User {
    public Pacient() {
    }

    public Pacient(String name, String cpf, String password) {
        this.name = name;
        this.cpf = cpf;
        this.password = password;
    }
}
