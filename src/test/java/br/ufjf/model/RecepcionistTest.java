package br.ufjf.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RecepcionistTest {
    @Test
    @DisplayName("Deve criar recepcionista com construtor padrão")
    void testConstrutorPadrao() {
        Receptionist receptionista = new Receptionist();
        assertNotNull(receptionista);
    }
}
