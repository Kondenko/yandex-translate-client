package com.vladimirkondenko.yamblz.model.database;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class Database {

    private Realm database;

    public Database(Realm database) {
        this.database = database;
    }

    public static Database getDatabase() {
        return new Database(
                Realm.getInstance(new RealmConfiguration.Builder().build())
        );
    }

    public Realm getRealm() {
        return database;
    }

    public static void init(Context context) {
        Realm.init(context);
    }

    public void performTransaction(Runnable action) {
        database.executeTransaction(transaction -> action.run());
    }

}
