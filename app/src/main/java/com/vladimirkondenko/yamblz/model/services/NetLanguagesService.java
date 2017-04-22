package com.vladimirkondenko.yamblz.model.services;

import com.vladimirkondenko.yamblz.model.entities.Languages;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetLanguagesService {

    @POST("getLangs")
    Single<Languages> getAvailableLanguages(@Query("ui") String ui);

}
