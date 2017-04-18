package com.vladimirkondenko.yamblz.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Translation extends RealmObject {

    public Translation() {}

    @PrimaryKey
    private long timestamp = 0;

    @SerializedName("code")
    @Expose
    private Integer code;

    @SerializedName("lang")
    @Expose
    private String lang = "";

    @SerializedName("text")
    @Expose
    private RealmList<RealmString> text;

    public Translation(long timestamp, Integer code, String lang, RealmList<RealmString> text) {
        this.timestamp = timestamp;
        this.code = code;
        this.lang = lang;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public String getLang() {
        return lang;
    }

    public RealmList<RealmString> getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
