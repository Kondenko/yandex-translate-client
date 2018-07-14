package com.vladimirkondenko.yamblz;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.regex.Pattern;

public class DebugApp extends App {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                        .withDescendingOrder()
                        .withLimit(1000)
                        .databaseNamePattern(Pattern.compile(".+\\.realm"))
                        .build())
                .build());
    }

}
