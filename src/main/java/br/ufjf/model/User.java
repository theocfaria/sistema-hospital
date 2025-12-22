package br.ufjf.model;

public abstract class User {
    protected String name;
    protected String cpf;
    protected String password;

    public User() {

    }

    public User(String name, String cpf, String password) {
        this.name = name;
        this.cpf = cpf;
        this.password = password;
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
