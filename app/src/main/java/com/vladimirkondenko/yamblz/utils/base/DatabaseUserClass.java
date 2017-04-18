package com.vladimirkondenko.yamblz.utils.base;

import com.vladimirkondenko.yamblz.model.database.Database;

import io.realm.Realm;

public abstract class DatabaseUserClass {

    protected Realm realm;

    public DatabaseUserClass(Database database) {
        this.realm = database.getRealm();
    }

    public void performTransaction(Runnable operation) {
        realm.executeTransaction(transaction -> operation.run());
    }

}
