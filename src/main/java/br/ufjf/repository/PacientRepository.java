package br.ufjf.repository;

import java.util.ArrayList;

import java.lang.reflect.Type;
import java.util.List;
import br.ufjf.model.Documento;
import com.google.gson.reflect.TypeToken;

import br.ufjf.model.Pacient;

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
                paciente.getDocumento().add(documento);
                saveAll(pacientes);
                return;
            }
        }
    }
}
