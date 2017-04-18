package com.vladimirkondenko.yamblz.dagger.modules;

import com.vladimirkondenko.yamblz.BuildConfig;
import com.vladimirkondenko.yamblz.model.database.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    public RealmConfiguration provideRealmConfiguration() {
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder();
        if (BuildConfig.DEBUG) builder.deleteRealmIfMigrationNeeded();
        RealmConfiguration configuration = builder.build();
        Realm.setDefaultConfiguration(configuration);
        return configuration;
    }

    @Provides
    public Realm provideRealm(RealmConfiguration configuration) {
        return Realm.getInstance(configuration);
    }

    @Provides
    public Database provideDatabase(Realm realm) {
        return new Database(realm);
    }

}
