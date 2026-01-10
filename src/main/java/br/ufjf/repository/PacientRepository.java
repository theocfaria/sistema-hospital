package br.ufjf.repository;

import java.util.ArrayList;

import java.lang.reflect.Type;
import java.util.List;

import br.ufjf.model.*;
import com.google.gson.reflect.TypeToken;

public class PacientRepository extends BaseUserRepository<Pacient> {
    public PacientRepository() {
        super(Pacient.class);
    }

    @Override
    protected Type getListType() {
        return new TypeToken<ArrayList<Pacient>>() {
        }.getType();
    }

    public void updateDocumentos(String cpf, Documento documento) {
        List<Pacient> pacientes = loadAll();

        for (Pacient paciente : pacientes) {
            if (paciente.getCpf().equals(cpf)) {
                if(paciente.getDocumentos()==null){
                    paciente.setDocumentos(new ArrayList<>());
                }

                if(!isExists(documento, paciente)) {
                    paciente.getDocumentos().add(documento);
                    saveAll(pacientes);
                    return;
                }
            }
        }
    }

    public boolean isExists(Documento documento, Pacient paciente){
        List<Documento> documentos = paciente.getDocumentos();

        if(documentos==null){
            return false;
        }

        for(Documento d : documentos){
            if(documento.getTipoDocumento().equals(d.getTipoDocumento()) &&
            documento.getConsulta().getId().equals(d.getConsulta().getId())){

                return true;
            }
        }

        return false;
    }

    public void updateStatusInternacao(String cpf, StatusInternacao statusInternacao) {
        List<Pacient> pacientes = loadAll();

        for(Pacient paciente : pacientes){
            if(paciente.getCpf().equals(cpf)){
                paciente.setStatusInternacao(statusInternacao);
                saveAll(pacientes);
            }
        }
    }
}
