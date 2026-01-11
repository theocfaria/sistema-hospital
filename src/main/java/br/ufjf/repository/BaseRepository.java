package br.ufjf.repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.ufjf.model.User;

abstract class BaseRepository<T> {
    private final String FILEPATH;
    private final Gson gson;

    public BaseRepository(Class<T> c) {
        this.FILEPATH = "src/main/java/br/ufjf/data/" + c.getSimpleName().toLowerCase() + "s.json";
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    protected abstract Type getListType();

    protected String getFilePath() {
        return FILEPATH;
    }

    public void saveAll(List<T> list) {
        File file = new File(FILEPATH);

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public List<T> loadAll() {
        File file = new File(FILEPATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(FILEPATH)) {
            // isso aqui é pra salvar o tipo da lista durante o runtime
            List<T> list = gson.fromJson(reader, getListType());

            return (list != null) ? list : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Erro ao carregar.");
            return new ArrayList<>();
        }
    }


}
