package vn.ghtk.training.microservices.utilities.id;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UUIDUtil {
    public static String genUUID() {
        return UUID.randomUUID().toString();
    }

    public static String genUUID(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);

        return UUID.nameUUIDFromBytes(bytes).toString();
    }


}
