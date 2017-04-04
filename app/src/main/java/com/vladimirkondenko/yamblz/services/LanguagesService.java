package com.vladimirkondenko.yamblz.services;

import com.vladimirkondenko.yamblz.model.LanguagesHolder;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LanguagesService {

    @POST("tr.json/getLangs")
    public Single<LanguagesHolder> getAvailableLanguages(@Query("ui") String ui);

}
