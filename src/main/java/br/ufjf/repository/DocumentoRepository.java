package br.ufjf.repository;

import br.ufjf.model.Consulta;
import br.ufjf.model.Documento;
import br.ufjf.model.Medico;
import br.ufjf.model.TipoDocumento;
import br.ufjf.serializer.MedicoResumoSerializer;
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

public class DocumentoRepository extends BaseRepository<Documento> {
    private final Gson gsonResumo;

    public DocumentoRepository(){
        super(Documento.class);

        this.gsonResumo = new GsonBuilder()
                .registerTypeAdapter(Medico.class, new MedicoResumoSerializer())
                .setPrettyPrinting()
                .create();
    }

    @Override
    protected Type getListType() {
        return new TypeToken<ArrayList<Documento>>() {
        }.getType();
    }

    public void save(Documento documento) {
        List<Documento> documentos = loadAll();
        documentos.add(documento);

        try (FileWriter writer = new FileWriter(getFilePath())) {
            gsonResumo.toJson(documentos, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Documento findByCPF(String cpf) {
        List<Documento> elements = loadAll();

        for (Documento element : elements) {
            if (element.getConsulta().getPaciente().getCpf().equals(cpf)) {
                return element;
            }
        }
        return null;
    }

    public Documento findDocumento(Consulta consulta, TipoDocumento tipoDocumento){
        List<Documento> documentos = loadAll();

        for(Documento documento: documentos){
            if(documento.getTipoDocumento().equals(tipoDocumento) && documento.getConsulta().getId().equals(consulta.getId())
                    && documento.getConsulta()!=null && documento.getConsulta().getId()!=null) {
                return documento;
            }
        }
        return null;
    }
}
