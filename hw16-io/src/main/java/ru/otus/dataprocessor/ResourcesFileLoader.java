package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        try {
            ArrayList<Measurement> measurements = new ArrayList<>();
            byte[] bytes = this.getClass().getClassLoader().getResourceAsStream(fileName).readAllBytes();
            ObjectMapper mapper = new ObjectMapper();
            Iterator<JsonNode> iterator = mapper.readTree(bytes).iterator();

            while (iterator.hasNext()) {
                JsonNode next = iterator.next();
                String name = next.path("name").asText();
                double value = next.path("value").asDouble();
                measurements.add(new Measurement(name, value));
            }
            return measurements;
        } catch (IOException e) {
            throw new FileProcessException("Error read file");
        }
    }
}
