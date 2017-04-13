package com.vladimirkondenko.yamblz.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


public class RxNetworkBroadcastReceiver extends BroadcastReceiver {

    private PublishSubject<Boolean> subject = PublishSubject.create();

    private Context context;

    public RxNetworkBroadcastReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        subject.onNext(isOnline());
    }

    public Observable<Boolean> register() {
        context.registerReceiver(this, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        return subject;
    }

    public void unregister() {
        context.unregisterReceiver(this);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

}
