package com.vladimirkondenko.yamblz;

import java.io.IOException;
import java.io.InputStream;

import static org.robolectric.internal.bytecode.RobolectricInternals.getClassLoader;

public abstract class TestUtils {

    private static final String DEFAULT_JSON_ENCODING = "UTF-8";

    public static String getJsonFromTestAssets(String jsonName) {
        String json = "{}";
        try {
            InputStream inputStream = getFileInputStream(jsonName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, DEFAULT_JSON_ENCODING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private static InputStream getFileInputStream(String jsonName) throws IOException {
        return getClassLoader().getResourceAsStream(jsonName);
    }

}
