package br.ufjf.factory;

import br.ufjf.model.Medico;
import br.ufjf.repository.MedicoRepository;
import net.datafaker.Faker;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

public class MedicoFactory {
    public static void populate(int range) {
        List<Medico> list = new ArrayList<>();
        Faker faker = new Faker(Locale.of("pt", "BR"));

        for (int i = 0; i < range; i++) {

            String name = faker.expression("Dr. #{name.full_name}");
            String cpf = faker.cpf().valid(true);
            Medico medico = new Medico(name, cpf, "password");

            list.add(medico);
        }

        MedicoRepository repo = new MedicoRepository();
        repo.saveAll(list);
    }
}
