package br.ufjf.repository;

import br.ufjf.model.Consulta;
import br.ufjf.model.Medico;
import br.ufjf.model.Pacient;
import br.ufjf.model.StatusConsulta;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<Consulta> findByMedico(Medico medico) {
        List<Consulta> elements = loadAll();

        List<Consulta> consultasMedico = new ArrayList<>();
        for (Consulta element : elements) {
            if (element.getMedico().getCpf().equals(medico.getCpf())) {
                consultasMedico.add(element);
            }
        }
        return consultasMedico;
    }

    public List<Pacient> findPacientesbyMedico(Medico medico) {
        List<Consulta> consultas = findByMedico(medico);
        List<Pacient> pacientes = new ArrayList<>();

        for(Consulta consulta : consultas){
            boolean exist = false;

            for(Pacient paciente : pacientes){
                if (paciente.getCpf().equals(consulta.getPaciente().getCpf())) {
                    exist = true;
                    break;
                }
            }

            if(!exist){
                pacientes.add(consulta.getPaciente());
            }
        }
        return pacientes;
    }

    public void updateStatus(Consulta consulta, StatusConsulta statusConsulta) {
        List<Consulta> consultas = loadAll();

        for(Consulta c : consultas){
            if(c.getId().equals(consulta.getId())){
                c.setStatusConsulta(statusConsulta);
                saveAll(consultas);
            }
        }
    }
}
