package br.ufjf.model;

public class Documento {

    private Consulta consulta;
    private TipoDocumento tipoDocumento;
    private String informacao;
    private Integer diasAfastamento;

    public Documento(Consulta consulta, TipoDocumento tipoDocumento, String informacao, Integer diasAfastamento) {
        this.consulta = consulta;
        this.tipoDocumento = tipoDocumento;
        this.informacao = informacao;
        this.diasAfastamento = diasAfastamento;
    }

    public Documento(Consulta consulta, TipoDocumento tipoDocumento, String informacao) {
        this.consulta = consulta;
        this.tipoDocumento = tipoDocumento;
        this.informacao = informacao;
    }

    public Consulta getConsulta() {
        return this.consulta;
    }

    public TipoDocumento getTipoDocumento() {
        return this.tipoDocumento;
    }

    public String getInformacao() {
        return informacao;
    }

    public Integer getDiasAfastamento() {
        return diasAfastamento;
    }

    public String getData() {
        return (consulta != null) ? consulta.getData().toString() : "";
    }

    public String getMedico() {
        return (consulta != null) ? consulta.getMedico().getName() : "Não informado";
    }

    public String getTipo() {
        return (tipoDocumento != null) ? tipoDocumento.toString() : "";
    }

    public String getDescricao() {
        return informacao;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setInformacao(String informacao) {
        this.informacao = informacao;
    }

    public String getDescricaoClinicaConsulta(){ return consulta.getDescricaoClinica();}

    public void setDiasAfastamento(Integer diasAfastamento) {
        this.diasAfastamento = diasAfastamento;
    }
}
