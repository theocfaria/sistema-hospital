package br.ufjf;

import br.ufjf.factory.MedicoFactory;
import br.ufjf.factory.PacientFactory;

public class Main {
    public static void main(String[] args) {
        PacientFactory.populate();
        MedicoFactory.populate();
    }
}
