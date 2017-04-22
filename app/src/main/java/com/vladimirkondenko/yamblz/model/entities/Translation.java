package com.vladimirkondenko.yamblz.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vladimirkondenko.yamblz.utils.Utils;

import java.util.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Translation extends RealmObject {

    public static final String FIELD_NAME_PRIMARY_KEY = "id";
    public static final String FIELD_NAME_TIMESTAMP = "timestamp";
    public static final String FIELD_NAME_BOOKMARKED = "isBookmarked";
    public static final String FIELD_NAME_SAVED_TO_HISTORY = "isSavedToHistory";

    @PrimaryKey
    private int id = 0;

    private long timestamp = 0;

    private boolean isBookmarked = false;

    private boolean isSavedToHistory = false;

    private String inputText;

    @SerializedName("code")
    @Expose
    private Integer code;

    @SerializedName("lang")
    @Expose
    private String direction = "";

    @SerializedName("text")
    @Expose
    private RealmList<RealmString> result;

    public Translation() {}

    public Translation(long timestamp, Integer code, String direction, RealmList<RealmString> result) {
        this.timestamp = timestamp;
        this.code = code;
        this.direction = direction;
        this.result = result;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public boolean isSavedToHistory() {
        return isSavedToHistory;
    }

    public void setSavedToHistory(boolean savedToHistory) {
        isSavedToHistory = savedToHistory;
    }

    public String getDirection() {
        return direction;
    }

    public RealmList<RealmString> getResult() {
        return result;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getInputText() {
        return inputText;
    }

    public int calculateId() {
        return Utils.hashCode(inputText, direction);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
