package com.vladimirkondenko.yamblz;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Always subscribeOn and observeOn Schedulers.trampoline()
 * for immediate execution.
 */
public class RxRule implements TestRule {

    private final Scheduler trampoline = Schedulers.trampoline();

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> trampoline);
                RxJavaPlugins.setComputationSchedulerHandler(scheduler -> trampoline);
                RxJavaPlugins.setIoSchedulerHandler(scheduler -> trampoline);
                RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> trampoline);
                try {
                    base.evaluate();
                } finally {
                    RxAndroidPlugins.reset();
                    RxJavaPlugins.reset();
                }
            }
        };
    }

}
