package am.itspace.converter.util;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CsvUtil {

    private static final CsvMapper csvMapper = new CsvMapper();

    public static <T> List<T> readCSV(Class<T> clazz, InputStream stream) throws IOException {
        CsvSchema schema = csvMapper.schemaFor(clazz).withHeader().withColumnReordering(true);
        ObjectReader reader = csvMapper.readerFor(clazz).with(schema);
        return reader.<T>readValues(stream).readAll();
    }

}
