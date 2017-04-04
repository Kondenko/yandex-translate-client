package com.vladimirkondenko.yamblz.model.services;

import com.vladimirkondenko.yamblz.model.entities.LanguagesHolder;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LanguagesService extends BaseService {

    @POST("tr.json/getLangs")
    public Single<LanguagesHolder> getAvailableLanguages(@Query("ui") String ui);

}
