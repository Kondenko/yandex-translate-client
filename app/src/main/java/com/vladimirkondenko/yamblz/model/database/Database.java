package com.vladimirkondenko.yamblz.model.database;

import android.content.Context;

import javax.inject.Inject;

import io.realm.Realm;

public class Database {

    @Inject
    public Database(Realm realm) {
        this.realm = realm;
    }

    public static void init(Context context) {
        Realm.init(context);
    }

    private Realm realm;

    public Realm getRealm() {
        return realm;
    }

    public void close() {
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }
}


