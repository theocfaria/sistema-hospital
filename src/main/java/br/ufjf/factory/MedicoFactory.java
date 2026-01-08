package br.ufjf.factory;

import br.ufjf.model.Medico;
import br.ufjf.repository.MedicoRepository;
import net.datafaker.Faker;

import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MedicoFactory {
    public static void populate(int range) {
        List<Medico> list = new ArrayList<>();
        Faker faker = new Faker(Locale.of("pt", "BR"));

        for (int i = 0; i < range; i++) {

            String name = faker.expression("Dr. #{name.full_name}");
            String cpf = faker.cpf().valid(true);
            String password = "password";
            List<DayOfWeek> days = geraDiasDaSemana();
            String inicio = geraInicioAtendimento();
            String fim = geraFinalAtendimento();

            Medico medico = new Medico(name, cpf, password, days, inicio, fim, 30);

            list.add(medico);
        }

        MedicoRepository repo = new MedicoRepository();
        repo.saveAll(list);
    }

    private static List<DayOfWeek> geraDiasDaSemana() {
        Faker faker = new Faker(Locale.of("pt", "BR"));

        List<DayOfWeek> days = Arrays.asList(
                DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);

        List<DayOfWeek> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            DayOfWeek diaGerado = faker.options().nextElement(days);
            while (list.contains(diaGerado))
                diaGerado = faker.options().nextElement(days);
            list.add(diaGerado);
        }
        return list;
    }

    private static String geraInicioAtendimento() {
        Faker faker = new Faker(Locale.of("pt", "BR"));

        LocalTime inicio = LocalTime.of(8, 0);
        int slots = 8; // para gerar algum horário até 08:00 + 8*30 = 12:00

        int slotSorteado = faker.number().numberBetween(0, slots);
        LocalTime horarioFinal = inicio.plusMinutes(slotSorteado * 30);

        return horarioFinal.toString();
    }

    private static String geraFinalAtendimento() {
        Faker faker = new Faker(Locale.of("pt", "BR"));

        LocalTime inicio = LocalTime.of(12, 0);
        int slots = 12; // para gerar algum horário até 08:00 + 8*30 = 12:00

        int slotSorteado = faker.number().numberBetween(0, slots);
        LocalTime horarioFinal = inicio.plusMinutes(slotSorteado * 30);

        return horarioFinal.toString();
    }
}
