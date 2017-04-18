package com.vladimirkondenko.yamblz;

public final class Const {

    public static final String API_KEY = "trnsl.1.1.20170315T172313Z.162bccb965880b7c.9feba3a457e1a4989e1bb689842c94d6bc9186e4";

    public static final String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";

    public static final int MAX_TEXT_LENGTH = 10000;

    // Request keys
    public static final String REQUEST_KEY_API_KEY = "key";

    public static final int INPUT_LANGUAGES_JSON_RES_EN = R.raw.input_languages_en;
    public static final int INPUT_LANGUAGES_JSON_RES_RU = R.raw.input_languages_ru;

    public static final String LANG_CODE_AUTO = "auto"; // Autodetection
    public static final String LANG_CODE_EN = "en";
    public static final String LANG_CODE_RU = "ru";

    // Animation durations
    public static final int ANIM_DURATION_DEFAULT = 350;
    public static final int ANIM_DURATION_LANG_SWITCH_SPINNER = 200;

}
