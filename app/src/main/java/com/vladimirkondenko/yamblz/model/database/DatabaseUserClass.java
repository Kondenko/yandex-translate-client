package com.vladimirkondenko.yamblz.model.database;

import com.vladimirkondenko.yamblz.model.database.Database;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;

public abstract class DatabaseUserClass {

    protected Realm realm;

    public DatabaseUserClass(Database database) {
        this.realm = database.getRealm();
    }

    public void performTransaction(Runnable operation) {
        realm.executeTransaction(transaction -> operation.run());
    }

    protected void save(RealmObject object) {
        performTransaction(() -> realm.copyToRealmOrUpdate(object));
    }

    protected void delete(RealmObject object) {
        object.deleteFromRealm();
    }

}
