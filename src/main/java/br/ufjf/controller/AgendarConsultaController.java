package br.ufjf.controller;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import br.ufjf.model.*;
import br.ufjf.repository.ConsultaRepository;
import br.ufjf.repository.MedicoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AgendarConsultaController implements DashboardController<Pacient>, Initializable {
    @FXML
    private ComboBox<Medico> doctorComboBox;
    @FXML
    private TableView<Slot> availabilityTable;
    @FXML
    private TableColumn<Slot, LocalDate> colData;
    @FXML
    private TableColumn<Slot, String> colDiaSemana;
    @FXML
    private TableColumn<Slot, LocalTime> colHorario;
    @FXML
    private Button btnConfirmar;

    private ConsultaRepository consulta_repo = new ConsultaRepository();
    private MedicoRepository medico_repo = new MedicoRepository();
    private Pacient user;
    private ObservableList<Slot> listaSlots = FXCollections.observableArrayList();

    @Override
    public void setUser(Pacient user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colDiaSemana.setCellValueFactory(new PropertyValueFactory<>("diaSemanaFormatado"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));

        availabilityTable.setItems(listaSlots);

        List<Medico> todosMedicos = medico_repo.loadAll();

        List<Medico> medicos = todosMedicos.stream().filter(m -> m.getStatusAtendimento() != null && m.getStatusAtendimento() == StatusAtendimento.DISPONIVEL).toList();

        doctorComboBox.setItems(FXCollections.observableArrayList(medicos));

        doctorComboBox.setConverter(new javafx.util.StringConverter<Medico>() {
            @Override
            public String toString(Medico m) {
                return (m == null) ? "" : m.getName();
            }

            @Override
            public Medico fromString(String string) {
                return null;
            }
        });

        doctorComboBox.setOnAction(event -> atualizarSlotsDisponiveis());
        btnConfirmar.setOnAction(event -> confirmarAgendamento());
    }

    @FXML
    private void onDoctorSelected() {
        Medico medicoSelecionado = doctorComboBox.getValue();

        if (medicoSelecionado == null)
            return;

        gerarSlotsDisponiveis(medicoSelecionado);
    }

    private void gerarSlotsDisponiveis(Medico medico) {
        listaSlots.clear();

        LocalTime inicio = medico.getInicio();
        LocalTime fim = medico.getFim();
        String duracao = medico.getDuracao();
        List<DayOfWeek> diasAtendimento = medico.getDiasAtendimento();
        
        int duracaoConsulta = Integer.parseInt(duracao);

        if (inicio == null || fim == null || diasAtendimento == null || diasAtendimento.isEmpty()) {
            return;
        }

        LocalDate hoje = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            LocalDate dataAlvo = hoje.plusDays(i);

            if (diasAtendimento.contains(dataAlvo.getDayOfWeek())) {
                LocalTime tempoAtual = inicio;

                while (tempoAtual.plusMinutes(duracaoConsulta).isBefore(fim.plusSeconds(1))) {

                    Consulta consultaExistente = consulta_repo.findConsulta(medico, dataAlvo, tempoAtual);

                    if (consultaExistente == null) {
                        listaSlots.add(new Slot(dataAlvo, tempoAtual, null));
                    }
                    tempoAtual = tempoAtual.plusMinutes(duracaoConsulta);
                }
            }
        }
    }

    private void atualizarSlotsDisponiveis() {
        Medico medico = doctorComboBox.getValue();
        if (medico == null)
            return;

        listaSlots.clear();
        
        LocalTime inicio = medico.getInicio();
        LocalTime fim = medico.getFim();
        String duracao = medico.getDuracao();
        List<DayOfWeek> diasAtendimento = medico.getDiasAtendimento();
        
        int duracaoConsulta = Integer.parseInt(duracao);

        if (inicio == null || fim == null || diasAtendimento == null || diasAtendimento.isEmpty()) {
            return;
        }

        LocalDate hoje = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            LocalDate dataAlvo = hoje.plusDays(i);

            if (diasAtendimento.contains(dataAlvo.getDayOfWeek())) {
                LocalTime tempoAtual = inicio;

                while (tempoAtual.plusMinutes(duracaoConsulta).isBefore(fim.plusSeconds(1))) {
                    Consulta consultaExistente = consulta_repo.findConsulta(medico, dataAlvo, tempoAtual);

                    if (consultaExistente == null) {
                        listaSlots.add(new Slot(dataAlvo, tempoAtual, null));
                    }

                    tempoAtual = tempoAtual.plusMinutes(duracaoConsulta);
                }
            }
        }
    }

    @FXML
    private void confirmarAgendamento() {
        Slot selecionado = availabilityTable.getSelectionModel().getSelectedItem();
        Medico medico = doctorComboBox.getValue();

        if (selecionado == null) {
            new Alert(Alert.AlertType.WARNING, "Selecione um horário na tabela.").show();
            return;
        }

        Consulta novaConsulta = new Consulta(selecionado.getData(), selecionado.getHorario(),
                user, medico, null, StatusConsulta.AGENDADA);
        consulta_repo.save(novaConsulta);

        new Alert(Alert.AlertType.INFORMATION, "Consulta marcada com sucesso.").show();

        atualizarSlotsDisponiveis();
    }
}
