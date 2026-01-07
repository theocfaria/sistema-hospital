package br.ufjf.controller;

import br.ufjf.model.Consulta;
import br.ufjf.model.Medico;
import br.ufjf.model.Pacient;
import br.ufjf.model.User;
import br.ufjf.repository.ConsultaRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoricoClinicoController implements DashboardController {

    @FXML private ComboBox<Pacient> cmbPacientes;
    @FXML private TableView<Consulta> tabelaHistorico;
    @FXML private TableColumn<Consulta, LocalDate> colData;
    @FXML private TableColumn<Consulta, String> colDescricao;
    @FXML private TableColumn<Consulta, String> colMedico;
    @FXML private RadioButton rbApto;
    @FXML private RadioButton rbNaoApto;
    @FXML private TextArea txtEvolucao;
    @FXML private Button btnSalvar;
    @FXML private Button btnEdit;

    private ObservableList<Consulta> listaConsulta = FXCollections.observableArrayList();
    private Medico medico;
    private Consulta consultaSelecionada;
    private ConsultaRepository consultaRepository;

    @FXML
    public void initialize() {
        consultaRepository = new ConsultaRepository();
        configurarColunas();
        txtEvolucao.setEditable(false);
        btnSalvar.setDisable(true);
    }

    @Override
    public void setUser(User user){
        this.medico = (Medico)user;
        carregarPacientes();
    }

    public void configurarColunas(){
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricaoClinica"));
        colMedico.setCellValueFactory(new PropertyValueFactory<>("nomeMedico"));

        tabelaHistorico.setItems(listaConsulta);
    }

    public void carregarPacientes(){
        cmbPacientes.getItems().clear();

        List<Pacient> pacientes = consultaRepository.findPacientesbyMedico(medico);
        cmbPacientes.getItems().addAll(pacientes);
    }

    @FXML
    public void pacienteSelecionado(){
        Pacient pacienteSelected = cmbPacientes.getValue();

        if(pacienteSelected != null){
            carregarTabela(pacienteSelected);
        }
    }

    public void  carregarTabela(Pacient pacienteSelected){
        tabelaHistorico.getItems().clear();
        List<Consulta> consultas = consultaRepository.findConsultabyPaciente(pacienteSelected.getCpf());

        listaConsulta.addAll(consultas);
        tabelaHistorico.setItems(listaConsulta);
    }

    @FXML
    public void editarDescricao(){
        consultaSelecionada = tabelaHistorico.getSelectionModel().getSelectedItem();

        if(consultaSelecionada==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecione alguma consulta para alterar a descrição");
            alert.show();
            return;
        }

        txtEvolucao.setEditable(true);
        btnSalvar.setDisable(false);
    }

    @FXML
    public void salvarDescricao(){
        String descricaoClinica = txtEvolucao.getText();

        if(descricaoClinica == null||descricaoClinica.isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Digite a descrição/evolução da consulta do paciente");
            alert.show();
            return;
        }

        consultaSelecionada.setDescricaoClinica(descricaoClinica);
        consultaRepository.updateDescricao(consultaSelecionada, descricaoClinica);

        tabelaHistorico.refresh();

        txtEvolucao.setEditable(false);
        txtEvolucao.clear();
        btnSalvar.setDisable(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Descrição registrada com sucesso!");
        alert.show();
    }
}