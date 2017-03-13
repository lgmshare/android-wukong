package com.lgmshare.component.http;

/**
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/5/9 11:21
 */
public interface HttpRequest {

    /**
     * 异步get请求
     *
     * @param tag      标记
     * @param url      请求url
     * @param callback 回调
     */
    void get(Object tag, String url, HttpResponseCallback callback);

    /**
     * 异步get请求
     *
     * @param tag      标记
     * @param url      请求url
     * @param params   请求参数
     * @param callback 回调
     */
    void get(Object tag, String url, RequestParams params, HttpResponseCallback callback);

    /**
     * 异步get请求
     *
     * @param tag      标记
     * @param url      请求url
     * @param params   请求参数
     * @param headers  请求头
     * @param callback 回调
     */
    void get(Object tag, String url, RequestParams params, RequestHeaders headers, HttpResponseCallback callback);

    /**
     * 异步post请求
     *
     * @param tag      标记
     * @param url      请求url
     * @param callback 回调
     */
    void post(Object tag, String url, HttpResponseCallback callback);

    /**
     * 异步post请求（文本方式）
     *
     * @param tag      标记
     * @param url      请求url
     * @param data     请求参数
     * @param callback 回调
     */
    void post(Object tag, String url, String data, HttpResponseCallback callback);

    /**
     * 异步post请求（文本方式）
     *
     * @param tag         标记
     * @param url         请求url
     * @param data        请求参数
     * @param contentType 请求参数
     * @param callback    回调
     */
    void post(Object tag, String url, String data, String contentType, HttpResponseCallback callback);

    /**
     * 异步post请求（文本方式）
     *
     * @param tag         标记
     * @param url         请求url
     * @param data        请求参数
     * @param contentType 请求参数
     * @param headers     请求头
     * @param callback    回调
     */
    void post(Object tag, String url, String data, String contentType, RequestHeaders headers, HttpResponseCallback callback);

    /**
     * 异步post请求(表单方式)
     *
     * @param tag      标记
     * @param url      请求url
     * @param params   参数
     * @param callback 回调
     */
    void post(Object tag, String url, RequestParams params, HttpResponseCallback callback);


    /**
     * 异步post请求(表单方式)
     *
     * @param tag      标记
     * @param url      请求url
     * @param params   参数
     * @param headers  请求头
     * @param callback 回调
     */
    void post(Object tag, String url, RequestParams params, RequestHeaders headers, HttpResponseCallback callback);

    /**
     * 同步get请求
     *
     * @param tag      标记
     * @param url      请求url
     * @param params   请求参数
     * @param headers  请求头
     * @param callback 回调
     */
    void syncGet(Object tag, String url, RequestParams params, RequestHeaders headers, HttpResponseCallback callback);

    /**
     * 同步post请求(表单方式)
     *
     * @param tag      标记
     * @param url      请求url
     * @param params   参数
     * @param headers  请求头
     * @param callback 回调
     */
    void syncPost(Object tag, String url, RequestParams params, RequestHeaders headers, HttpResponseCallback callback);

    /**
     * 取消请求
     *
     * @param tag
     */
    void cancel(Object tag);

    /**
     * 取消所有请求
     */
    void cancelAll();

}
