package br.ufjf.repository;

import java.util.ArrayList;

import java.lang.reflect.Type;
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
}
