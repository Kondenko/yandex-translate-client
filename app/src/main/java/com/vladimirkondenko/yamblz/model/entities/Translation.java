package com.vladimirkondenko.yamblz.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vladimirkondenko.yamblz.utils.Utils;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Translation extends RealmObject {

    public static final String FIELD_NAME_PRIMARY_KEY = "id";
    public static final String FIELD_NAME_TIMESTAMP = "timestamp";
    public static final String FIELD_NAME_DIRECTION = "direction";
    public static final String FIELD_NAME_INPUT_TEXT = "inputText";
    public static final String FIELD_NAME_TRANSLATED_TEXT = "translatedText";
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
    private RealmList<RealmString> translatedText;

    public Translation() {
    }

    public String getFormattedTranslatedText() {
        StringBuilder resultsBuilder = new StringBuilder();
        for (RealmString string : translatedText) {
            resultsBuilder.append(String.format("%s\n", string.getValue()));
        }
        return new String(resultsBuilder);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int calculateId() {
        return Utils.hashCode(inputText, direction);
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

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getInputText() {
        return inputText;
    }

    public String getDirection() {
        return direction;
    }

    public RealmList<RealmString> getTranslatedText() {
        return translatedText;
    }

}
