package br.ufjf.repository;

import br.ufjf.model.Consulta;
import br.ufjf.model.Medico;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository extends BaseRepository<Consulta> {
    public ConsultaRepository() {
        super(Consulta.class);
    }

    @Override
    protected Type getListType() {
        return new TypeToken<ArrayList<Consulta>>() {
        }.getType();
    }

    public void save(Consulta consulta) {
        List<Consulta> consultas = loadAll();
        consultas.add(consulta);
        saveAll(consultas);
    }

    public Consulta findConsulta(Medico medico, LocalDate dataAtual, LocalTime horaAtual) {
        List<Consulta> elements = loadAll();

        for (Consulta element : elements) {
            if (element.getMedico().getCpf().equals(medico.getCpf()) && element.getData().equals(dataAtual) && element.getHora().equals(horaAtual)) {
                return element;
            }
        }
        return null;
    }
}
