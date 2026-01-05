package br.ufjf.model;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pacient extends User {
    private List<Documento> documentos;

    public Pacient() {
        super();
    }

    public Pacient(String name, String cpf, String password, List<Documento> documentos) {
        super(name, cpf, password);
        this.documentos = documentos != null ? documentos : new ArrayList<>();
    }

    public List<Documento> getDocumento() {
        return documentos;
    }

    public void setDocumento(List<Documento> documento) {
        this.documentos = documentos != null ? documentos : new ArrayList<>();
    }

    public String getDashboardFxml() {
        return "Pacient.fxml";
    }
}
