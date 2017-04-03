package com.vladimirkondenko.yamblz;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.robolectric.internal.bytecode.RobolectricInternals.getClassLoader;

public class RestServiceTestHelper {

    @Deprecated
    public static String getStringFromFile(Context context, String filePath) throws Exception {
        final InputStream stream = context.getResources().getAssets().open(filePath);
        String ret = convertStreamToString(stream);
        stream.close();
        return ret;
    }

    /**
     * Gets a string from a file in /assets folder
     * @param fileName the name of the file to be loaded
     * @return contents of the file
     * @throws Exception
     */
    public static String getStringFromFile(String fileName) throws Exception {
        InputStream stream = getClassLoader().getResourceAsStream(fileName);
        String ret = convertStreamToString(stream);
        stream.close();
        return ret;
    }

    private static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

}
