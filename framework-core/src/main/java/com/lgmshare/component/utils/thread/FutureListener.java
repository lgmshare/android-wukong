package com.lgmshare.component.utils.thread;

public interface FutureListener<T> {

    void onFutureBegin(Future<T> future);

    void onFutureDone(Future<T> future);
}