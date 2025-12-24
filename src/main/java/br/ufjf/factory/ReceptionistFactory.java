package br.ufjf.factory;

import br.ufjf.model.Receptionist;
import br.ufjf.repository.ReceptionistRepository;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReceptionistFactory {
    public static void populate(int range){
        List<Receptionist> list = new ArrayList<>();
        Faker faker = new Faker(Locale.of("pt", "BR"));

        for(int i = 0; i < range; i++){
            String name = faker.name().fullName();
            String cpf = faker.cpf().valid(true);

            Receptionist receptionist = new Receptionist(name, cpf, "password");
            list.add(receptionist);
        }

        ReceptionistRepository repo = new ReceptionistRepository();
        repo.saveAll(list);
    }
}
