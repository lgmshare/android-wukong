package com.lgmshare.component.http;

/**
 * 请求回调
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/5/6 10:04
 */
public abstract class HttpResponseCallback {

    /**
     * 请求开始
     */
    public void onStart() {

    }

    /**
     * 请求结束
     */
    public void onFinish() {

    }

    /**
     * 请求取消
     */
    public void onCancel() {

    }

    /**
     * 请求进度
     *
     * @param totalSize 文件大小
     * @param progress  下载进度
     * @param offset    下载速度
     */
    public void onProgress(long totalSize, long progress, long offset) {

    }

    /**
     * 请求成功回调
     *
     * @param statusCode
     * @param responseBody
     */
    public abstract void onSuccess(int statusCode, String responseBody);

    /**
     * 请求失败回调
     *
     * @param statusCode
     * @param responseBody
     * @param error
     */
    public abstract void onFailure(int statusCode, String responseBody, Throwable error);
}
