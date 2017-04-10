package com.vladimirkondenko.yamblz.utils;


public class ErrorCodeThrowable extends Throwable {

    private int errorCode = 0;

    public ErrorCodeThrowable(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
