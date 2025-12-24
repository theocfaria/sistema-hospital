package br.ufjf.factory;

import br.ufjf.repository.PacientRepository;
import br.ufjf.repository.ReceptionistRepository;

public class Factory {
    public static void populate(int range){
        MedicoFactory.populate(range);
        PacientFactory.populate(range);
        ReceptionistFactory.populate(range);
    }
}
