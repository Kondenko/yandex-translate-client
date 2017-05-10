package com.vladimirkondenko.yamblz.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kondenko on 24.04.2017.
 */

public class DetectedLanguage {

    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("langs")
    @Expose
    public String lang;

}
