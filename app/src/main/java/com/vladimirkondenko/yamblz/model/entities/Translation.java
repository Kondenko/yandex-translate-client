package com.vladimirkondenko.yamblz.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Translation extends RealmObject {

    public Translation() {}

    @SerializedName("code")
    @Expose
    private Integer code;

    @SerializedName("lang")
    @Expose
    private String lang;

    @SerializedName("text")
    @Expose
    private RealmList<RealmString> text = null;

    public Translation(Integer code, String lang, RealmList<RealmString> text) {
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

}
