package com.vladimirkondenko.yamblz.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.vladimirkondenko.yamblz.model.entities.RealmString;

import java.io.IOException;
import java.lang.reflect.Type;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Creates a Gson object which handles RealmObject wrappers for primitive types.
 * The code snippet is taken from <a href='https://gist.github.com/cmelchior/1a97377df0c49cd4fca9'>this</a> gist,
 * which is referenced in the official Realm <a href='https://realm.io/docs/java/latest/#primitive-lists'>documentation</a>.
 *
 * @see com.vladimirkondenko.yamblz.model.entities.RealmString
 */

public final class RealmGson {

    public static Gson create() {
        Type token = new TypeToken<RealmList<RealmString>>() {
        }.getType();
        return new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(token, new TypeAdapter<RealmList<RealmString>>() {
                    @Override
                    public void write(JsonWriter out, RealmList<RealmString> value) throws IOException {
                    }

                    @Override
                    public RealmList<RealmString> read(JsonReader in) throws IOException {
                        RealmList<RealmString> list = new RealmList<>();
                        in.beginArray();
                        while (in.hasNext()) {
                            list.add(new RealmString(in.nextString()));
                        }
                        in.endArray();
                        return list;
                    }
                })
                .create();
    }

}
