package br.ufjf.repository;

import br.ufjf.model.Medico;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepository {
    private final String FILEPATH = "src/main/java/br/ufjf/data/medicos.json";
    private final Gson gson;

    public MedicoRepository() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public void saveAll(List<Medico> list) {
        File file = new File(FILEPATH);

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public List<Medico> loadAll() {
        File file = new File(FILEPATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(FILEPATH)) {
            // isso aqui é pra salvar o tipo da lista durante o runtime
            Type listType = new TypeToken<ArrayList<Medico>>() {
            }.getType();
            List<Medico> list = gson.fromJson(reader, listType);

            return (list != null) ? list : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Erro ao carregar.");
            return new ArrayList<>();
        }
    }

    public Medico findByID(String id) {
        List<Medico> medicos = loadAll();

        for (Medico medico : medicos) {
            if (medico.getId().toString().equals(id)) {
                return medico;
            }
        }
        return null;
    }

    public Medico findByCPF(String cpf) {
        List<Medico> medicos = loadAll();

        for (Medico medico : medicos) {
            if (medico.getCpf().equals(cpf)) {
                return medico;
            }
        }
        return null;
    }

    public void updateByID(String id, String name, String password) {
        List<Medico> medicos = loadAll();

        for (Medico medico : medicos) {
            if (medico.getId().toString().equals(id)) {
                medico.setName(name);
                medico.setPassword(password);

                saveAll(medicos);
            }
        }
    }

    public void deleteByID(String id) {
        List<Medico> aux = loadAll();
        List<Medico> list = new ArrayList<>();

        for (Medico medico : aux) {
            if (medico.getId().toString().equals(id)) {
                continue;
            }
            list.add(medico);
        }
        saveAll(list);
    }

    public void deleteByCpf(String cpf) {
        List<Medico> aux = loadAll();
        List<Medico> list = new ArrayList<>();

        for (Medico medico : aux) {
            if (medico.getCpf().toString().equals(cpf)) {
                continue;
            }
            list.add(medico);
        }
        saveAll(list);
    }
}
