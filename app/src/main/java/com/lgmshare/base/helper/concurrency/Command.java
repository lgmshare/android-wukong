/*
 * Copyright (C) 2015 Actor LLC. <https://actor.im>
 */

package com.lgmshare.base.helper.concurrency;

public interface Command<T> {
    void start(CommandCallback<T> callback);
}
