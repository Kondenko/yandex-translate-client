package com.vladimirkondenko.yamblz.utils;

import com.vladimirkondenko.yamblz.CustomRobolectricTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(CustomRobolectricTestRunner.class)
public class LanguageUtilsTest {

    @Test
    public void shouldConvertToDirection() throws Exception {
        assertEquals(LanguageUtils.langsToDirection("ru", "en"), "ru-en");
    }

    @Test
    public void shouldParseDirection() throws Exception {
        String direction = "en-ru";
        String[] result = LanguageUtils.parseDirection(direction);
        assertThat(result, hasItemInArray("ru"));
        assertThat(result, hasItemInArray("en"));
        assertThat(result.length, equalTo(2));
    }

}