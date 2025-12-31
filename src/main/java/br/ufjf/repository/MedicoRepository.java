package br.ufjf.repository;

import br.ufjf.model.Medico;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepository extends BaseUserRepository<Medico> {
    public MedicoRepository() {
        super(Medico.class);
    }

    @Override
    protected Type getListType() {
        return new TypeToken<ArrayList<Medico>>() {
        }.getType();
    }

    public void updateHorarios(String cpf, List<DayOfWeek> dias, LocalTime inicio, LocalTime fim, int duracao) {
        List<Medico> elements = loadAll();

        for (Medico element : elements) {
            if (element.getCpf().equals(cpf)) {
                element.setDiasAtendimento(dias);
                element.setInicio(inicio);
                element.setFim(fim);
                element.setDuracao(duracao);
                saveAll(elements);
            }
        }
    }
}
