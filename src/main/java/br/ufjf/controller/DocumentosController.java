package br.ufjf.controller;

import br.ufjf.model.*;
import br.ufjf.repository.ConsultaRepository;
import br.ufjf.repository.DocumentoRepository;
import br.ufjf.repository.PacientRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class DocumentosController implements DashboardController<Medico>{

    @FXML private TextField txtPaciente;
    @FXML private TextField txtCpf;
    @FXML private TextField txtDataHora;
    @FXML private TextArea txtAtestado;
    @FXML private TextArea txtReceita;
    @FXML private TextField txtDiasAfastamento;
    @FXML private ComboBox<Consulta> cbConsulta;
    @FXML private Button btn;
    ConsultaRepository  consultaRepository;
    DocumentoRepository documentoRepository;
    PacientRepository pacientRepository;

    Consulta consultaAtual;
    Medico medico;

    @FXML
    public void initialize(){
        consultaRepository = new ConsultaRepository();
        documentoRepository = new DocumentoRepository();
        pacientRepository = new PacientRepository();
    }

    @Override
    public void setUser(Medico user){
        this.medico=user;
        carregarConsultas();
    }

    public void carregarConsultas(){
        cbConsulta.getItems().clear();

        List<Consulta> consultas = consultaRepository.findByMedico(medico);

        for(Consulta consulta:consultas){
            if(consulta.getStatusConsulta()==StatusConsulta.REALIZADA){
                cbConsulta.getItems().add(consulta);
            }
        }
        cbConsulta.setDisable(false);
    }

    @FXML
    public void consultaSelecionada(){
        consultaAtual = cbConsulta.getValue();

        if(consultaAtual != null){
            preencherDadosConsulta(consultaAtual);
        }
    }

    private void setConsultaAtual(Consulta consultaAtual){
        this.consultaAtual = consultaAtual;
    }

    public void preencherDadosConsulta(Consulta consultaAtual){
        txtPaciente.setText(consultaAtual.getPaciente().getName());
        txtCpf.setText(consultaAtual.getPaciente().getCpf());
        txtDataHora.setText(consultaAtual.getData().toString() + " | " + consultaAtual.getHora().toString());

        txtPaciente.setEditable(false);
        txtCpf.setEditable(false);
        txtDataHora.setEditable(false);
    }

    @FXML
    private void gerarAtestado() {
        String informacao = txtAtestado.getText();
        int diasAfastamento = Integer.parseInt(txtDiasAfastamento.getText());

        if(consultaAtual==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Selecione uma consulta");
            alert.show();
            return;
        }

        if(txtAtestado.getText().isBlank() || txtDiasAfastamento.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Nem todos os campos foram preenchidos");
            alert.show();
            return;
        }
        if(documentoRepository.findDocumento(consultaAtual,TipoDocumento.ATESTADO)!=null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Já existe um atestado para essa consulta");
            alert.show();
            return;
        }
        Documento atestado = new Documento(consultaAtual, TipoDocumento.ATESTADO, informacao, diasAfastamento);
        documentoRepository.save(atestado);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Atestado gerado com sucesso");
        alert.show();
    }

    @FXML
    private void enviarAtestado() {
        if(consultaAtual==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Selecione uma consulta");
            alert.show();
            return;
        }

        Documento documento = documentoRepository.findDocumento(consultaAtual, TipoDocumento.ATESTADO);

        if(documento==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Documento não encotrado");
            alert.show();
            return;
        }

        Pacient paciente = consultaAtual.getPaciente();
        pacientRepository.updateDocumentos(paciente.getCpf(),documento);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Atestado enviado com sucesso");
        alert.show();
    }

    @FXML
    private void gerarReceita() {
        String informacao = txtReceita.getText();

        if(consultaAtual==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Selecione uma consulta");
            alert.show();
            return;
        }

        if(txtReceita.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Nem todos os campos foram preenchidos");
            alert.show();
            return;
        }
        if(documentoRepository.findDocumento(consultaAtual,TipoDocumento.RECEITA)!=null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Já existe uma receita para essa consulta");
            alert.show();
            return;
        }
        Documento receita = new Documento(consultaAtual, TipoDocumento.RECEITA, informacao);
        documentoRepository.save(receita);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Receita gerada com sucesso");
        alert.show();
    }

    @FXML
    private void enviarReceita() {
        if(consultaAtual==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Selecione uma consulta");
            alert.show();
            return;
        }

        Documento documento = documentoRepository.findDocumento(consultaAtual, TipoDocumento.RECEITA);

        if(documento==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Documento não encotrado");
            alert.show();
            return;
        }

        String cpfPaciente = consultaAtual.getPaciente().getCpf();
        Pacient paciente = pacientRepository.findByCPF(cpfPaciente);
        pacientRepository.updateDocumentos(paciente.getCpf(),documento);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Receita enviada com sucesso");
        alert.show();
    }
}
