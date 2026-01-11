package br.ufjf.serializer;

import br.ufjf.model.Medico;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class MedicoResumoSerializer implements JsonSerializer<Medico> {

    @Override
    public JsonElement serialize(Medico medico, Type type,
                                 JsonSerializationContext context) {

        JsonObject json = new JsonObject();
        json.addProperty("cpf", medico.getCpf());
        json.addProperty("name", medico.getName());

        return json;
    }
}
