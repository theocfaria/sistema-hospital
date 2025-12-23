package br.ufjf.factory;

import net.datafaker.Faker;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import br.ufjf.model.Pacient;
import br.ufjf.repository.PacientRepository;

public class PacientFactory {
    public static void populate() {
        List<Pacient> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Faker faker = new Faker(Locale.of("pt", "BR"));

            String name = faker.name().fullName();
            String cpf = faker.cpf().valid(true);
            Pacient pacient = new Pacient(name, cpf, "password");

            list.add(pacient);
        }

        PacientRepository repo = new PacientRepository();
        repo.saveAll(list);
    }
}
