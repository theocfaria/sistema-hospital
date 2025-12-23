package br.ufjf.repository;

import br.ufjf.model.Medico;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MedicoRepository extends BaseRepository<Medico> {
    public MedicoRepository() {
        super(Medico.class);
    }

    @Override
    protected Type getListType() {
        return new TypeToken<ArrayList<Medico>>() {
        }.getType();
    }
}
