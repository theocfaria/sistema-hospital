package br.ufjf.controller;

import br.ufjf.model.Medico;
import br.ufjf.model.Pacient;
import br.ufjf.model.Receptionist;
import br.ufjf.model.User;
import br.ufjf.repository.MedicoRepository;
import br.ufjf.repository.PacientRepository;
import br.ufjf.repository.ReceptionistRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.List;

public class LoginController {
    @FXML private TextField TxtCpf;
    @FXML private TextField TxtPassword;
    @FXML private Button BtnLogin;
    PacientRepository  pacientRepository = new PacientRepository();
    MedicoRepository medicoRepository = new MedicoRepository();
    ReceptionistRepository receptionistRepository = new ReceptionistRepository();

    private User verificarLogin(){
        String cpf = TxtCpf.getText();
        String password = TxtPassword.getText();

        List<Pacient> pacientes = pacientRepository.loadAll();

        for(Pacient paciente: pacientes){
            if(paciente.getCpf().equals(cpf) && paciente.getPassword().equals(password)){
                return paciente;
            }
        }

        List<Medico> medicos = medicoRepository.loadAll();

        for(Medico medico: medicos){
            if(medico.getCpf().equals(cpf) && medico.getPassword().equals(password)){
                return medico;
            }
        }

        List<Receptionist> receptionists = receptionistRepository.loadAll();

        for(Receptionist receptionist: receptionists){
            if(receptionist.getCpf().equals(cpf) && receptionist.getPassword().equals(password)){
                return receptionist;
            }
        }
        return null;
    }

    @FXML
    private void login(){
        User tipo = verificarLogin();

        if(tipo==null){
            new Alert(Alert.AlertType.ERROR, "CPF e/ou senha incorretos. Não foi possível fazer o login").showAndWait();
            TxtCpf.setText("");
            TxtPassword.setText("");
            TxtCpf.requestFocus();
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Login feito corretamente! " + tipo.getClass().getSimpleName()).showAndWait();
        }
    }


}
