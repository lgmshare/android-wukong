package com.lgmshare.base.http;

/**
 * @author: lim
 * @description: TODO
 * @email: lgmshare@gmail.com
 * @datetime 2015/12/30
 */
public abstract class HttpResponseHandler<T> {

    public void onStart() {}

    public abstract void onSuccess(T t);

    public abstract void onFailure(int code, String error);

    public void onCancel() {}

    public void onFinish() {}

}
