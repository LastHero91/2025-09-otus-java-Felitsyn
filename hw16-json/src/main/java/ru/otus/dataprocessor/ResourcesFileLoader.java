package ru.otus.dataprocessor;

import java.io.*;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {
    private static final Logger logger = LoggerFactory.getLogger(ResourcesFileLoader.class);

    private final ObjectMapper mapper = JsonMapper.builder().build();
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = ResourcesFileLoader.class
                .getClassLoader()
                .getResource(fileName)
                .getPath();
    }

    /***
     * Читает файл, парсит и возвращает результат
     * @return список Measurement
     */
    @Override
    public List<Measurement> load() {
        try {
            String jsonStr = getJsonFromFile();
            List<Measurement> measurements = mapper.readValue(jsonStr, new TypeReference<>(){});

            logger.info("json десериализован в List<Measurement>: {}", measurements);
            return measurements;
        } catch (Exception e) {
            throw new FileProcessException(e);
        }
    }

    private String getJsonFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            logger.info("json прочитан из файла {}: {}", fileName, sb);
            return sb.toString();
        }
    }
}
