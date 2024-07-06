package web.spring;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static web.spring.utils.routing.ArgumentsEncryptionUtils.decryptToObjectPair;
import static web.spring.utils.routing.ArgumentsEncryptionUtils.encryptToJson;

public class EncryptionUtilsTest {

    @Test
    public void testEncryptToJsonAndDecryptToObjectPair() {
        String originalString = "Hello";
        Integer originalInteger = 123;

        // Encrypting
        String encryptedJson = encryptToJson(originalString, originalInteger);
        assertNotNull(encryptedJson);

        // Decrypting
        Pair<String, Integer> decryptedPair = decryptToObjectPair(encryptedJson, String.class, Integer.class);
        assertNotNull(decryptedPair);

        assertEquals(originalString, decryptedPair.getFirst());
        assertEquals(originalInteger, decryptedPair.getSecond());
    }

}
