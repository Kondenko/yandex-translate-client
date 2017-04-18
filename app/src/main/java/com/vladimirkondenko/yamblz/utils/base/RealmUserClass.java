package com.vladimirkondenko.yamblz.utils.base;

import io.realm.Realm;

public abstract class RealmUserClass {

    protected Realm realm;

    public RealmUserClass(Realm realm) {
        this.realm = realm;
    }

    public void performTransaction(Runnable operation) {
        realm.executeTransaction(transaction -> operation.run());
    }

}
