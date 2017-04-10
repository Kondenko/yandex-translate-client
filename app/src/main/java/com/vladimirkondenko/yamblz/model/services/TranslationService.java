package com.vladimirkondenko.yamblz.model.services;

import com.vladimirkondenko.yamblz.model.entities.Translation;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TranslationService {

    String KEY_LANG = "lang";
    String KEY_TEXT = "text";

    @POST("translate")
    Single<Translation> getTranslation(@Query(KEY_LANG) String lang, @Query(KEY_TEXT) String text);

}
