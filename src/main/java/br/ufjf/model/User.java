package br.ufjf.model;

import java.util.UUID;

public abstract class User {
    protected UUID id;
    protected String name;
    protected String cpf;
    protected String password;

    public User() {

    }

    public User(String name, String cpf, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.cpf = cpf;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCpf() {
        return this.cpf;
    }

    public String getPassword() {
        return this.password;
    }
}
