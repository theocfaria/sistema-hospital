package br.ufjf.controller;

import br.ufjf.model.Consulta;
import br.ufjf.model.Medico;
import br.ufjf.model.StatusConsulta;
import br.ufjf.repository.ConsultaRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.DayOfWeek; // Enum nativo
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AcoesConsultaController {

    @FXML private Label lblNome, lblEmail, lblTelefone;
    @FXML private DatePicker dpNovaData;
    @FXML private ComboBox<String> cbHorarios;

    private Consulta consultaSelecionada;
    private ConsultaRepository repository = new ConsultaRepository();

    public void setConsulta(Consulta consulta) {
        this.consultaSelecionada = consulta;

        lblNome.setText("Nome: " + consulta.getPaciente().getName());
        lblEmail.setText("Email: " + consulta.getPaciente().getEmail());
        lblTelefone.setText("Telefone: " + consulta.getPaciente().getTelefone());

        configurarCalendario();
    }

    private void configurarCalendario() {

        List<DayOfWeek> diasAtendimento = consultaSelecionada.getMedico().getDiasAtendimento();

        dpNovaData.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (empty || date == null) return;

                boolean ehPassado = date.isBefore(LocalDate.now());

                boolean muitoLonge = date.isAfter(LocalDate.now().plusWeeks(1));

                boolean medicoAtende = diasAtendimento.contains(date.getDayOfWeek());

                if (ehPassado || muitoLonge || !medicoAtende) {
                    setDisable(true);
                    setStyle("-fx-background-color: #eeeeee;");
                }
            }
        });
    }

    @FXML
    private void atualizarHorarios() {
        LocalDate data = dpNovaData.getValue();
        if (data == null) return;

        cbHorarios.getItems().clear();
        Medico m = consultaSelecionada.getMedico();

        LocalTime atual = m.getInicio();
        LocalTime fim = m.getFim();
        String duracao = m.getDuracao();

        while (atual.isBefore(fim)) {
            cbHorarios.getItems().add(atual.toString());
            atual = atual.plusMinutes(Integer.parseInt(duracao));
        }
    }

    @FXML
    private void handleConfirmarReagendamento() {
        LocalDate novaData = dpNovaData.getValue();
        String novoHorario = cbHorarios.getValue();

        if (novaData == null || novoHorario == null) {
            exibirAlerta("Selecione data e horário!");
            return;
        }

        consultaSelecionada.setData(novaData);
        consultaSelecionada.setHora(novoHorario);
        consultaSelecionada.setStatusConsulta(StatusConsulta.AGENDADA);

        repository.updateDataHora(consultaSelecionada);

        exibirAlerta("Consulta reagendada com sucesso!");
        handleCancelar();
    }

    @FXML
    private void handleCancelar() {
        ((Stage) lblNome.getScene().getWindow()).close();
    }

    private void exibirAlerta(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }
}