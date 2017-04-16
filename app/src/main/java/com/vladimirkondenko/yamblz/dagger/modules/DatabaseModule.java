package com.vladimirkondenko.yamblz.dagger.modules;

import com.vladimirkondenko.yamblz.model.database.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    public Database provideDatabase() {
        return Database.getDatabase();
    }

    @Provides
    @Singleton
    public Realm provideRealm(Database database) { return database.getRealm(); }

}
