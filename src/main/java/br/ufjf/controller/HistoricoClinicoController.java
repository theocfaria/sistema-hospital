package br.ufjf.controller;

import br.ufjf.model.Consulta;
import br.ufjf.model.Medico;
import br.ufjf.model.Pacient;
import br.ufjf.model.User;
import br.ufjf.repository.ConsultaRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoricoClinicoController implements DashboardController {

    @FXML private ComboBox<Pacient> cmbPacientes;
    @FXML private TableView<Consulta> tabelaHistorico;
    @FXML private TableColumn<Consulta, LocalDate> colData;
    @FXML private TableColumn<Consulta, String> colMedico;
    @FXML private RadioButton rbApto;
    @FXML private RadioButton rbNaoApto;
    @FXML private Button btnSalvar;
    @FXML private Button btnEdit;
    private Medico medico;
    private ConsultaRepository consultaRepository;

    @FXML
    public void initialize() {
        consultaRepository = new ConsultaRepository();

    }

    @Override
    public void setUser(User user){
        this.medico = (Medico)user;
        carregarPacientes();
        carregarTabela();
    }

    public void carregarPacientes(){
        cmbPacientes.getItems().clear();

        List<Pacient> pacientes = consultaRepository.findPacientesbyMedico(medico);
        cmbPacientes.getItems().addAll(pacientes);
    }

    public void  carregarTabela(){

    }

}