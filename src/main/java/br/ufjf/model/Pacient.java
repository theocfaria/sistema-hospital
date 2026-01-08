package br.ufjf.model;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pacient extends User {
    private List<Documento> documentos;
    private StatusInternacao statusInternacao;

    public Pacient() {
        super();
    }

    public Pacient(String name, String cpf, String password, List<Documento> documentos, StatusInternacao statusInternacao) {
        super(name, cpf, password);
        this.documentos = documentos != null ? documentos : new ArrayList<>();
        this.statusInternacao = statusInternacao;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documento) {
        this.documentos = documentos != null ? documentos : new ArrayList<>();
    }

    public void setStatusInternacao(StatusInternacao statusInternacao){ this.statusInternacao = statusInternacao; }
    public StatusInternacao getStatusInternacao(){ return statusInternacao; }

    public String getDashboardFxml() {
        return "DashboardPaciente.fxml";
    }

    @Override
    public String toString() {
        return getName();
    }
}
