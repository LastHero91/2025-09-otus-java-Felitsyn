package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public class FileSerializer implements Serializer {
    private static final Logger logger = LoggerFactory.getLogger(FileSerializer.class);

    private final ObjectMapper mapper = JsonMapper
            .builder()
            .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
            .build();
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    /***
     * формирует результирующий json и сохраняет его в файл
     * @param data - сгруппированный список Measurement по name и суммированным value
     */
    @Override
    public void serialize(Map<String, Double> data) {
        try {
            String jsonStr = mapper.writeValueAsString(data);
            logger.info("json отсортирован по ключу и сериализован в строку: {}", jsonStr);

            saveJsonToFile(jsonStr);
        } catch (Exception e) {
            throw new FileProcessException(e);
        }
    }

    private void saveJsonToFile(String jsonStr) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(jsonStr);
        }
        logger.info("json сохранен в файл {}: {}", fileName, jsonStr);
    }
}
