package com.vladimirkondenko.yamblz.utils;

public abstract class ErrorCodes {

    public static final int SUCCESS = 200;
    public static final int API_KEY_INVALID = 401;
    public static final int API_KEY_BLOCKED = 402;
    public static final int DAILY_LIMIT_EXCEEDED = 404;
    public static final int TEXT_TOO_LONG = 413;
    public static final int TEXT_CANT_BE_TRANSLATED = 422;
    public static final int UNSUPPORTED_TRANSLATION_DIRECTION = 501;

}
