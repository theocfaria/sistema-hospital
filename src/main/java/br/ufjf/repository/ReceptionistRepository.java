package br.ufjf.repository;

import br.ufjf.model.Receptionist;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ReceptionistRepository extends BaseUserRepository<Receptionist> {

    public ReceptionistRepository() { super(Receptionist.class); }

    @Override
    protected Type getListType() {
        return new TypeToken<ArrayList<Receptionist>>() {
        }.getType();
    }

}
