package web.spring.utils.routing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.util.Pair;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class ArgumentsEncryptionUtils {

    private static final String AES_KEY = "0123456789abcdef";
    private static final String AES_ALGORITHM = "AES";

//    public static String encryptToJson(Object obj1, Object obj2) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(Pair.of(obj1, obj2));
//            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
//            SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            byte[] encryptedBytes = cipher.doFinal(json.getBytes(StandardCharsets.UTF_8));
//            return Base64.getEncoder().encodeToString(encryptedBytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static <T, U> Pair<T, U> decryptToObjectPair(String encryptedJson, Class<T> class1, Class<U> class2) {
//        try {
//            // Dekodowanie z Base64
//            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedJson);
//
//            // Deszyfrowanie
//            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
//            SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
//            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
//
//            // Deserializacja do Map<String, Object>
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> map = objectMapper.readValue(new String(decryptedBytes, StandardCharsets.UTF_8),
//                    objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
//
//            // Przekształcenie Map<String, Object> do Pair<T, U>
//            T first = objectMapper.convertValue(map.get("first"), class1);
//            U second = objectMapper.convertValue(map.get("second"), class2);
//            return Pair.of(first, second);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null; // Można obsłużyć błąd w odpowiedni sposób
//        }
//    }

    private static String encryptJson(String json) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(json.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String decryptJson(String encryptedJson) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedJson);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(StandardCharsets.UTF_8), AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static String encryptToJson(Object obj1, Object obj2) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(Pair.of(obj1, obj2));
            return encryptJson(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encryptToJson(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(obj);
            return encryptJson(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T, U> Pair<T, U> decryptToObjectPair(String encryptedJson, Class<T> class1, Class<U> class2) {
        try {
            String json = decryptJson(encryptedJson);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(json, objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
            T first = objectMapper.convertValue(map.get("first"), class1);
            U second = objectMapper.convertValue(map.get("second"), class2);
            return Pair.of(first, second);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T decryptToObject(String encryptedJson, Class<T> clazz) {
        try {
            String json = decryptJson(encryptedJson);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
