package RF.Notely.app.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JSONUtils() {
    }

    public static <T> String marshal(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Errore nella serializzazione in JSON", e);
        }
    }

    public static <T> T unmarshal(Class<T> klass, String json) {
        try {
            return objectMapper.readValue(json, klass);
        } catch (Exception e) {
            throw new RuntimeException("Errore nella deserializzazione JSON", e);
        }
    }
}