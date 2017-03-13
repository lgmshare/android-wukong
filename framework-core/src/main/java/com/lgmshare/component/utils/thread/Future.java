package com.lgmshare.component.utils.thread;

public interface Future<T> {

    void cancel();

    boolean isCancelled();

    boolean isDone();

    T get();

    void waitDone();

    void setCancelListener(CancelListener cancelListener);

    interface CancelListener {
        void onCancel();
    }
}
