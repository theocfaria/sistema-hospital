package br.ufjf.controller;

import br.ufjf.model.Consulta;
import br.ufjf.model.Medico;
import br.ufjf.model.StatusConsulta;
import br.ufjf.model.User;
import br.ufjf.repository.ConsultaRepository;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AssiduidadeController implements DashboardController {

    @FXML private TableView<Consulta> tabelaConsultas;
    @FXML private TableColumn<Consulta, String> colPaciente;
    @FXML private TableColumn<Consulta, LocalDate> colData;
    @FXML private TableColumn<Consulta, LocalTime> colHorario;
    @FXML private TableColumn<Consulta, String> colMedico;
    @FXML private TableColumn<Consulta, StatusConsulta> colStatus;
    @FXML private Button btnReagendar;
    @FXML private Button btnAdiantar;

    private Medico medico;
    private ConsultaRepository consultaRepository;
    private AgendaController agenda;
    private ObservableList<Consulta> listaConsultas = FXCollections.observableArrayList();

    public void initialize(){
        configurarColunas();
        consultaRepository = new ConsultaRepository();
    }

    @Override
    public void setUser(User user){
        this.medico=(Medico)user;
        verificarFalta();
        carregarConsultas();
    }

    public void configurarColunas(){
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colMedico.setCellValueFactory(new PropertyValueFactory<>("nomeMedico"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusConsulta"));

        tabelaConsultas.setItems(listaConsultas);
    }

    public void carregarConsultas(){
        listaConsultas.clear();
        List<Consulta> consultas = consultaRepository.findByMedico(medico);

        listaConsultas.addAll(consultas);
    }

    public void verificarFalta(){
        List<Consulta> consultas = consultaRepository.findByMedico(medico);
        LocalTime horaAtual = LocalTime.now();
        LocalDate dataAtual = LocalDate.now();

        for(Consulta consulta : consultas){
            if(consulta.getStatusConsulta() == StatusConsulta.AGENDADA && consulta.getData().isEqual(dataAtual) &&
                    consulta.getHora().plusMinutes(medico.getDuracao()).isBefore(horaAtual)){

                consulta.setStatusConsulta(StatusConsulta.FALTOU);
            }
        }
        consultaRepository.saveAll(consultas);
    }

    @FXML
    public void alterarStatus(MouseEvent event){
        if(event.getClickCount() == 2){
            Consulta consultaSelecionada = tabelaConsultas.getSelectionModel().getSelectedItem();
            LocalTime horaAtual = LocalTime.now();
            LocalDate dataAtual = LocalDate.now();

            if(consultaSelecionada != null){
                if(consultaSelecionada.getStatusConsulta() == StatusConsulta.AGENDADA &&
                   consultaSelecionada.getHora().isBefore(horaAtual) && consultaSelecionada.getData().isEqual(dataAtual)){

                    consultaSelecionada.setStatusConsulta(StatusConsulta.REALIZADA);
                    tabelaConsultas.refresh();
                    consultaRepository.updateStatus(consultaSelecionada, StatusConsulta.REALIZADA);
                }
            }
        }
    }
}