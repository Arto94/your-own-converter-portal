package am.itspace.converter.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

    public static Gson getGson() {
        return new GsonBuilder()
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .create();
    }

}
