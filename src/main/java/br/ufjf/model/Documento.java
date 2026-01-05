package br.ufjf.model;

public class Documento {

    private Consulta consulta;
    private TipoDocumento tipoDocumento;
    private String informacao;
    private int diasAfastamento;

    public Documento(Consulta consulta, TipoDocumento tipoDocumento, String informacao, int  diasAfastamento) {
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

    public int getDiasAfastamento() {
        return diasAfastamento;
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

    public void setDiasAfastamento(int diasAfastamento) {
        this.diasAfastamento = diasAfastamento;
    }
}
