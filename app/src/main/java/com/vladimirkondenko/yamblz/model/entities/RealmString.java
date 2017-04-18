package com.vladimirkondenko.yamblz.model.entities;

import io.realm.RealmObject;

/**
 * Wraps a String objects.
 * It's needed because Realm does not support lists of primitives.
 * See <a href="http://stackoverflow.com/questions/30259676/can-i-have-arraylist-of-string-in-realm-object-android">StackOverflow question</a>.
 * */
public class RealmString extends RealmObject {

    private String value;

    public RealmString() {
    }

    public RealmString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
