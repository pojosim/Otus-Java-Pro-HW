package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        File file = new File(fileName);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, data);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
