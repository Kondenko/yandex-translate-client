package com.vladimirkondenko.yamblz.utils;

import org.junit.Test;

import java.util.LinkedHashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class UtilsTest {

    @Test
    public void shouldSortByValue() throws Exception {
        LinkedHashMap<String, String> initialMap = new LinkedHashMap<>();
        initialMap.put("en", "English");
        initialMap.put("es", "Spanish");
        initialMap.put("fr", "French");

        LinkedHashMap actualSortedMap = Utils.sortByValues(initialMap);

        LinkedHashMap<String, String> expectedSortedMap = new LinkedHashMap<>();
        expectedSortedMap.put("en", "English");
        expectedSortedMap.put("fr", "French");
        expectedSortedMap.put("es", "Spanish");

        assertThat(actualSortedMap, equalTo(expectedSortedMap));
    }

}