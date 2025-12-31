package br.ufjf.controller;

import br.ufjf.model.*;
import br.ufjf.repository.ConsultaRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendaController implements DashboardController {

    @FXML private TextField txtInicio, txtFim, txtDuracao;
    @FXML private CheckBox chkSegunda, chkTerca, chkQuarta, chkQuinta, chkSexta;
    @FXML private TableView<Slot> tabelaAgenda;
    @FXML private TableColumn<Slot, LocalDate> colData;
    @FXML private TableColumn<Slot, LocalTime> colHora;
    @FXML private TableColumn<Slot, String> colPaciente;
    private ConsultaRepository consultaRepository;
    private Medico medico;

    private ObservableList<Slot> listaSlots = FXCollections.observableArrayList();

    @Override
    public void setUser(User user){
        this.medico=(Medico)user;
    }

    @FXML
    public void initialize() {
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("horario"));
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("statusPaciente"));

        tabelaAgenda.setItems(listaSlots);

        consultaRepository = new ConsultaRepository();
    }

    @FXML
    private void gerarAgenda() {
        try {
            listaSlots.clear();

            LocalTime inicio = LocalTime.parse(txtInicio.getText());
            LocalTime fim = LocalTime.parse(txtFim.getText());
            int duracao = Integer.parseInt(txtDuracao.getText());

            List<DayOfWeek> diasSelecionados = obterDiasSelecionados();

            LocalDate hoje = LocalDate.now();
            for (int i = 0; i < 7; i++) {
                LocalDate dataAlvo = hoje.plusDays(i);

                if (diasSelecionados.contains(dataAlvo.getDayOfWeek())) {
                    LocalTime tempoAtual = inicio;

                    while (tempoAtual.plusMinutes(duracao).isBefore(fim.plusSeconds(1))) {
                        Consulta consulta = consultaRepository.findConsulta(medico, dataAlvo, tempoAtual);
                        Pacient paciente = (consulta!=null) ? consulta.getPaciente() : null;
                        listaSlots.add(new Slot(dataAlvo, tempoAtual, paciente));

                        tempoAtual = tempoAtual.plusMinutes(duracao);
                    }
                }
            }
        }  catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    private List<DayOfWeek> obterDiasSelecionados() {
        List<DayOfWeek> dias = new ArrayList<>();
        if (chkSegunda.isSelected()) dias.add(DayOfWeek.MONDAY);
        if (chkTerca.isSelected())   dias.add(DayOfWeek.TUESDAY);
        if (chkQuarta.isSelected())  dias.add(DayOfWeek.WEDNESDAY);
        if (chkQuinta.isSelected())  dias.add(DayOfWeek.THURSDAY);
        if (chkSexta.isSelected())   dias.add(DayOfWeek.FRIDAY);
        return dias;
    }
}
