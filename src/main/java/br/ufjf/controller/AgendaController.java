package br.ufjf.controller;

import br.ufjf.model.*;
import br.ufjf.repository.ConsultaRepository;
import br.ufjf.repository.MedicoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendaController implements DashboardController<Medico> {

    @FXML private TextField txtInicio, txtFim, txtDuracao;
    @FXML private CheckBox chkSegunda, chkTerca, chkQuarta, chkQuinta, chkSexta;
    @FXML private TableView<Slot> tabelaAgenda;
    @FXML private TableColumn<Slot, LocalDate> colData;
    @FXML private TableColumn<Slot, LocalTime> colHora;
    @FXML private TableColumn<Slot, String> colPaciente;

    private ConsultaRepository consultaRepository;
    private MedicoRepository medicoRepository;
    private Medico medico;

    private ObservableList<Slot> listaSlots = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("horario"));
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("statusPaciente"));

        tabelaAgenda.setItems(listaSlots);

        consultaRepository = new ConsultaRepository();
        medicoRepository = new MedicoRepository();

    }

    @Override
    public void setUser(Medico user){
        this.medico=user;

        txtInicio.setText(medico.getInicio().toString());
        txtFim.setText(medico.getFim().toString());
        txtDuracao.setText(Integer.toString(medico.getDuracao()));

        carregarDiasSelecionados();
    }

    @FXML
    private void gerarAgenda() {
        try {
            listaSlots.clear();

            List<DayOfWeek> diasSelecionados = medico.getDiasAtendimento();
            if(diasSelecionados.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Defina os dias para atendimento do medico antes de gerar agenda");
                alert.show();
                return;
            }

            LocalDate hoje = LocalDate.now();
            for (int i = 0; i < 7; i++) {
                LocalDate dataAlvo = hoje.plusDays(i);

                if (diasSelecionados.contains(dataAlvo.getDayOfWeek())) {
                    LocalTime tempoAtual = medico.getInicio();

                    while (tempoAtual.plusMinutes(medico.getDuracao()).isBefore(medico.getFim().plusSeconds(1))) {
                        Consulta consulta = consultaRepository.findConsulta(medico, dataAlvo, tempoAtual);
                        Pacient paciente = (consulta!=null) ? consulta.getPaciente() : null;
                        listaSlots.add(new Slot(dataAlvo, tempoAtual, paciente, consulta));

                        tempoAtual = tempoAtual.plusMinutes(medico.getDuracao());
                    }
                }
            }
        }  catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void salvarHorarios(){
        List<DayOfWeek> diasSelecionados = obterDiasSelecionados();
        LocalTime inicio = LocalTime.parse(txtInicio.getText());;
        LocalTime fim = LocalTime.parse(txtFim.getText());;;
        int duracao = Integer.parseInt(txtDuracao.getText());

        if(diasSelecionados.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nenhum dia para atendimento selecionado");
            alert.show();
            return;
        }

        medico.setDiasAtendimento(diasSelecionados);
        medico.setInicio(inicio);
        medico.setFim(fim);
        medico.setDuracao(duracao);

        medicoRepository.updateHorarios(medico.getCpf(), diasSelecionados, inicio, fim, duracao);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Dias de atendimento salvos com sucesso");
        alert.show();
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

    private void carregarDiasSelecionados(){
        List<DayOfWeek> diasAtendimento = medico.getDiasAtendimento();

        if(diasAtendimento.contains(DayOfWeek.MONDAY)) chkSegunda.setSelected(true);
        if(diasAtendimento.contains(DayOfWeek.TUESDAY)) chkTerca.setSelected(true);
        if(diasAtendimento.contains(DayOfWeek.WEDNESDAY)) chkQuarta.setSelected(true);
        if(diasAtendimento.contains(DayOfWeek.THURSDAY)) chkQuinta.setSelected(true);
        if(diasAtendimento.contains(DayOfWeek.FRIDAY)) chkSexta.setSelected(true);
    }
}
