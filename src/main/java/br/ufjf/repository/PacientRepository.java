package br.ufjf.repository;

import java.lang.reflect.Type;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.ufjf.model.Pacient;

public class PacientRepository {
    private final String FILEPATH = "src/main/java/br/ufjf/data/pacients.json";
    private final Gson gson;

    public PacientRepository() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public void saveAll(List<Pacient> list) {
        File file = new File(FILEPATH);

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public List<Pacient> loadAll() {
        File file = new File(FILEPATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(FILEPATH)) {
            // isso aqui é pra salvar o tipo da lista durante o runtime
            Type listType = new TypeToken<ArrayList<Pacient>>() {
            }.getType();
            List<Pacient> list = gson.fromJson(reader, listType);

            return (list != null) ? list : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Erro ao carregar.");
            return new ArrayList<>();
        }
    }
}
