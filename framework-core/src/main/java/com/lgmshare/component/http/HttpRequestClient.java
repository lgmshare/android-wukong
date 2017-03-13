package com.lgmshare.component.http;

import android.content.Context;
import android.util.Log;

import com.lgmshare.component.http.okhttp.OkHttpRequestClient;

/**
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/5/6 10:04
 */
public class HttpRequestClient {

    private static final String TAG = "HttpRequestClient";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_RANGE = "Content-Range";
    public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String ENCODING_GZIP = "gzip";
    public static final String CONTENT_TYPE_IMG = "image/png; charset=UTF-8";

    public static final int HTTP_OK = 200;
    public static final int HTTP_UNEXPECTED_CODE = -110;
    // 默认HTTP连接超时时间
    public static final int CONNECT_TIMEOUT = 30;

    private static HttpRequest mHttpRequest;

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        init(context, null);
    }

    public static void init(Context context, HttpConfiguration configuration) {
        if (configuration == null) {
            configuration = new HttpConfiguration.Builder().build();
        }
        if (mHttpRequest == null) {
            Log.d(TAG, "HttpRequestClient Init");
            // OKhttp实现
            mHttpRequest = new OkHttpRequestClient(context, configuration);
            // Volley实现
            //mHttpRequest = new VolleyRequestClient(context, configuration);
        } else {
            Log.d(TAG, "HttpRequestClient Initialized");
        }
    }

    public static void get(Object tag, String url, HttpResponseCallback callback) {
        get(tag, url, null, callback);
    }

    public static void get(Object tag, String url, RequestParams params, HttpResponseCallback callback) {
        get(tag, url, params, null, callback);
    }

    public static void get(Object tag, String url, RequestParams params, RequestHeaders headers, HttpResponseCallback callback) {
        mHttpRequest.get(tag, url, params, headers, callback);
    }

    public static void post(Object tag, String url, String data, String contentType, RequestHeaders headers, HttpResponseCallback callback) {
        mHttpRequest.post(tag, url, data, contentType, headers, callback);
    }

    public static void post(Object tag, String url, RequestParams params, RequestHeaders headers, HttpResponseCallback callback) {
        mHttpRequest.post(tag, url, params, headers, callback);
    }

    /**
     * 取消连接请求
     *
     * @param tag
     */
    public static void cancel(Object tag) {
        mHttpRequest.cancel(tag);
    }
}
