package br.ufjf.controller;

import br.ufjf.model.*;
import br.ufjf.repository.ConsultaRepository;
import br.ufjf.repository.DocumentoRepository;
import br.ufjf.repository.PacientRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoricoClinicoController implements DashboardController<Medico> {

    @FXML private ComboBox<Pacient> cmbPacientes;
    @FXML private TableView<Consulta> tabelaHistorico;
    @FXML private TableColumn<Consulta, LocalDate> colData;
    @FXML private TableColumn<Consulta, String> colDescricao;
    @FXML private TableColumn<Consulta, String> colMedico;
    @FXML private RadioButton rbApto;
    @FXML private RadioButton rbNaoApto;
    @FXML private RadioButton rbNaoInternado;
    @FXML private ToggleGroup grupoInternacao;
    @FXML private TextArea txtEvolucao;
    @FXML private Button btnSalvar;
    @FXML private Button btnEdit;

    private ObservableList<Consulta> listaConsulta = FXCollections.observableArrayList();
    private Medico medico;
    private Consulta consultaSelecionada;
    private ConsultaRepository consultaRepository;
    private PacientRepository pacientRepository;

    @FXML
    private void initialize() {
        consultaRepository = new ConsultaRepository();
        pacientRepository = new PacientRepository();

        txtEvolucao.setEditable(false);
        btnSalvar.setDisable(true);

        grupoInternacao = new ToggleGroup();

        rbApto.setToggleGroup(grupoInternacao);
        rbNaoApto.setToggleGroup(grupoInternacao);
        rbNaoInternado.setToggleGroup(grupoInternacao);

        rbApto.setDisable(true);
        rbNaoApto.setDisable(true);
        rbNaoInternado.setDisable(true);

        configurarColunas();
    }

    @Override
    public void setUser(Medico user){
        this.medico = user;
        carregarPacientes();
    }

    private void configurarColunas(){
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricaoClinica"));
        colMedico.setCellValueFactory(new PropertyValueFactory<>("nomeMedico"));

        tabelaHistorico.setItems(listaConsulta);
    }

    private void carregarPacientes(){
        cmbPacientes.getItems().clear();

        List<Pacient> pacientes = consultaRepository.findPacientesbyMedico(medico);
        cmbPacientes.getItems().addAll(pacientes);
    }

    @FXML
    private void pacienteSelecionado(){
        Pacient pacienteSelected = cmbPacientes.getValue();

        if(pacienteSelected != null){
            btnSalvar.setDisable(true);
            grupoInternacao.selectToggle(null);

            rbApto.setDisable(true);
            rbNaoApto.setDisable(true);
            rbNaoInternado.setDisable(true);
            txtEvolucao.clear();
            carregarTabela(pacienteSelected);
        }
    }

    private void  carregarTabela(Pacient pacienteSelected){
        tabelaHistorico.getItems().clear();
        List<Consulta> consultas = consultaRepository.findConsultabyPaciente(pacienteSelected.getCpf());

        for(Consulta consulta:consultas){
            if(consulta.getStatusConsulta()==StatusConsulta.REALIZADA){
                listaConsulta.add(consulta);
            }
        }
    }

    @FXML
    private void editarDescricao(){
        consultaSelecionada = tabelaHistorico.getSelectionModel().getSelectedItem();

        if(consultaSelecionada==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecione alguma consulta para alterar a descrição");
            alert.show();
            return;
        }

        String cpf = consultaSelecionada.getPaciente().getCpf();

        Pacient paciente = pacientRepository.findByCPF(cpf);
        StatusInternacao status = paciente.getStatusInternacao();

        if(status == StatusInternacao.APTO){
            rbApto.setSelected(true);
        }else if(status == StatusInternacao.NAO_APTO){
            rbNaoApto.setSelected(true);
        }else if(status == StatusInternacao.NAO_INTERNADO){
            rbNaoInternado.setSelected(true);
        }

        txtEvolucao.setEditable(true);

        if(consultaSelecionada.getDescricaoClinica()!=null){
            txtEvolucao.setText(consultaSelecionada.getDescricaoClinica());
            txtEvolucao.setEditable(false);
        }

        rbApto.setDisable(false);
        rbNaoApto.setDisable(false);
        rbNaoInternado.setDisable(false);

        btnSalvar.setDisable(false);
    }

    @FXML
    private void salvarDescricao(){
        if(!mudarStatusInternacao(consultaSelecionada)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selecione 1 opção para status de internação");
            alert.show();
            return;
        }

        String descricaoClinica = txtEvolucao.getText();

        consultaSelecionada.setDescricaoClinica(descricaoClinica);
        consultaRepository.updateDescricao(consultaSelecionada, descricaoClinica);
        tabelaHistorico.refresh();

        txtEvolucao.setEditable(false);
        txtEvolucao.clear();
        btnSalvar.setDisable(true);
        grupoInternacao.selectToggle(null);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Dados alterados com sucesso!");
        alert.show();
    }

    private boolean mudarStatusInternacao(Consulta consultaSelecionada){
        if(!rbApto.isSelected() && !rbNaoApto.isSelected() && !rbNaoInternado.isSelected()){
            return false;
        }

        Pacient paciente = pacientRepository.findByCPF(consultaSelecionada.getPaciente().getCpf());

        if(rbApto.isSelected()){
            paciente.setStatusInternacao(StatusInternacao.APTO);
            pacientRepository.updateStatusInternacao(paciente.getCpf(), StatusInternacao.APTO);
        }else if(rbNaoApto.isSelected()){
            paciente.setStatusInternacao(StatusInternacao.NAO_APTO);
            pacientRepository.updateStatusInternacao(paciente.getCpf(), StatusInternacao.NAO_APTO);
        }else if(rbNaoInternado.isSelected()){
            paciente.setStatusInternacao(StatusInternacao.NAO_INTERNADO);
            pacientRepository.updateStatusInternacao(paciente.getCpf(), StatusInternacao.NAO_INTERNADO);
        }
        return true;
    }
}