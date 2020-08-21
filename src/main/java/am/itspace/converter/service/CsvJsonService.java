package am.itspace.converter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static am.itspace.converter.util.CsvUtil.readCSV;
import static am.itspace.converter.util.JsonUtil.getGson;

@Service
@Slf4j
public class CsvJsonService {

    public ByteArrayResource csvToJson(MultipartFile file) {
        InputStream inputStream;
        ByteArrayResource resource = null;
        try {
            byte[] byteArr = file.getBytes();
            inputStream = new ByteArrayInputStream(byteArr);
            List<Object> objects = readCSV(Object.class, inputStream);
            String json = getGson().toJson(objects);
            resource = new ByteArrayResource(json.getBytes());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resource;
    }

    public ByteArrayResource jsonToCsv(MultipartFile file) {
        InputStream inputStream;
        ByteArrayResource resource = null;
        try {
            byte[] byteArr = file.getBytes();
            inputStream = new ByteArrayInputStream(byteArr);
            JsonNode jsonTree = new ObjectMapper().readTree(inputStream);
            CsvSchema.Builder builder = CsvSchema.builder();
            JsonNode firstObject = jsonTree.elements().next();
            firstObject.fieldNames().forEachRemaining(builder::addColumn);
            CsvSchema csvSchema = builder.build().withHeader();
            CsvMapper csvMapper = new CsvMapper();
            String value = csvMapper.writerFor(JsonNode.class)
                    .with(csvSchema)
                    .writeValueAsString(jsonTree);
            resource = new ByteArrayResource(value.getBytes());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resource;
    }

}
