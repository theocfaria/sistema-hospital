package br.ufjf.factory;

public class Factory {
    public static void populate(int range) {
        MedicoFactory.populate(range);
        PacientFactory.populate(range);
        ReceptionistFactory.populate(range);
    }
}
