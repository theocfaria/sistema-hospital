package br.ufjf.model;

import java.util.ArrayList;
import java.util.List;

public class Pacient extends User {
    private List<Documento> documentos;
    private StatusInternacao statusInternacao;
    private String email;
    private String telefone;
    private String endereco;

    public Pacient() {
        super();
    }

    public Pacient(String name, String cpf, String password){
        super(name, cpf, password);
    }

    public Pacient(String name, String cpf, String password, String email, String telefone, String endereco, StatusInternacao statusInternacao){
        super(name, cpf, password);
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.statusInternacao = statusInternacao;
    }

    public Pacient(String name, String cpf, String password, List<Documento> documentos,
            StatusInternacao statusInternacao) {
        super(name, cpf, password);
        this.documentos = documentos != null ? documentos : new ArrayList<>();
        this.statusInternacao = statusInternacao;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public List<Documento> getAtestados() {
        documentos = getDocumentos();
        List<Documento> atestados = new ArrayList<>();

        for (Documento documento : documentos) {
            if (documento.getTipoDocumento() == TipoDocumento.ATESTADO)
                atestados.add(documento);
        }

        return atestados;
    }

    public List<Documento> getReceitas() {
        documentos = getDocumentos();
        List<Documento> receitas = new ArrayList<>();

        for (Documento documento : documentos) {
            if (documento.getTipoDocumento() == TipoDocumento.RECEITA)
                receitas.add(documento);
        }

        return receitas;
    }

    public void setDocumentos(List<Documento> documento) {
        this.documentos = documentos != null ? documentos : new ArrayList<>();
    }

    public void setStatusInternacao(StatusInternacao statusInternacao) {
        this.statusInternacao = statusInternacao;
    }

    public StatusInternacao getStatusInternacao() {
        return statusInternacao;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDashboardFxml() {
        return "DashboardPaciente.fxml";
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getEmail() {
        return email;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }
}
