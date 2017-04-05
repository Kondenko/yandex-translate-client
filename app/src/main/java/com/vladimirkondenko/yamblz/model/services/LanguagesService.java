package com.vladimirkondenko.yamblz.model.services;

import com.vladimirkondenko.yamblz.model.entities.LanguagesHolder;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LanguagesService {
    @POST("tr.json/getLangs")
    Single<LanguagesHolder> getAvailableLanguages(@Query("ui") String ui);
}
