package com.iot.letthingsspeak.common.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class BasePresenter implements LifecycleObserver {

    public final String TAG = getClass().getSimpleName();

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected Lifecycle mLifecycle;

    public BasePresenter(Lifecycle mLifecycle) {
        this.mLifecycle = mLifecycle;
        if (mLifecycle != null) {
            mLifecycle.addObserver(this);
        }
    }


    public Lifecycle getLifecycle() {
        return mLifecycle;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Timber.d("onDestroy(): ");
        mCompositeDisposable.dispose();
        if (mLifecycle != null) {
            mLifecycle.removeObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Timber.d("onResume(): ");
    }


}
