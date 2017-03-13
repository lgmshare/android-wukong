/*
 * Copyright (C) 2015 Actor LLC. <https://actor.im>
 */

package com.lgmshare.base.helper.concurrency;

public interface CommandCallback<T> {
    void onResult(T result);

    void onError(Exception e);
}
