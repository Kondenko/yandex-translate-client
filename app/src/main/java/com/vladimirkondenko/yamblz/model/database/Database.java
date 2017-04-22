package com.vladimirkondenko.yamblz.model.database;

import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import com.vladimirkondenko.yamblz.BuildConfig;

import java.util.regex.Pattern;

import javax.inject.Inject;

import io.realm.Realm;

public class Database {

    private static final String TAG = "Database";

    @Inject
    public Database(Realm realm) {
        this.realm = realm;
    }

    public static void init(Context context) {
        if (BuildConfig.DEBUG) {
            Stetho.initialize(Stetho.newInitializerBuilder(context)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                    .enableWebKitInspector(RealmInspectorModulesProvider.builder(context)
                            .withDescendingOrder()
                            .withLimit(1000)
                            .databaseNamePattern(Pattern.compile(".+\\.realm"))
                            .build())
                    .build());
        }
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


