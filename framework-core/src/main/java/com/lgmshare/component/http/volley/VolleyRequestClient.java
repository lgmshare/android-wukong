package com.lgmshare.component.http.volley;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.lgmshare.component.http.HttpConfiguration;
import com.lgmshare.component.http.HttpRequest;
import com.lgmshare.component.http.HttpResponseCallback;
import com.lgmshare.component.http.RequestHeaders;
import com.lgmshare.component.http.RequestParams;
import com.lgmshare.component.http.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Volley
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/5/6 17:56
 */
public class VolleyRequestClient implements HttpRequest {

    //The default socket timeout in milliseconds
    private static final int DEFAULT_TIMEOUT_MS = 30 * 1000;
    //The default number of retries
    private static final int DEFAULT_MAX_RETRIES = 1;
    //The default backoff multiplier
    private static final float DEFAULT_BACKOFF_MULT = 1f;

    private static final int HTTP_OK = 200;
    private static final int HTTP_UNEXPECTED_CODE = 0;

    private static RequestQueue mRequestQueue;

    public VolleyRequestClient(final Context context, final HttpConfiguration configuration) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        mRequestQueue.start();
    }

    private void handleAsyncResult(int method, Object tag, String url, RequestParams params, RequestHeaders headers, final HttpResponseCallback callback) {
        if (callback != null) {
            callback.onStart();
        }
        if (method == Request.Method.GET) {
            url = Utils.getUrlWithQueryString(false, url, params);
        }
        StringRequest request = new StringRequest(method, url, new VolleyResponseCallback() {
            @Override
            public void onResponse(String response) {
                if (callback != null) {
                    callback.onFinish();
                    callback.onSuccess(HTTP_OK, response);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if (callback != null) {
                    callback.onFinish();
                    callback.onFailure(error.networkResponse != null ? error.networkResponse.statusCode : HTTP_UNEXPECTED_CODE, error.getMessage(), error);
                }
            }
        });
        request.setTag(tag);
        request.setSequence(2);
        request.setParams(buildParams(params));
        request.setHeaders(buildHeaders(headers));
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(request);
    }

    /**
     * 构建请求header
     *
     * @param headers
     * @return
     */
    private Map<String, String> buildHeaders(RequestHeaders headers) {
        Map<String, String> headerMap = new HashMap<String, String>();
        if (headers != null) {
            List<RequestHeaders.ValueWrapper> headerList = headers.getHeaderList();
            for (RequestHeaders.ValueWrapper header : headerList) {
                headerMap.put(header.key, header.value);
            }
        }
        return headerMap;
    }

    /**
     * 构建请求参数
     *
     * @param params
     * @return
     */
    private Map<String, String> buildParams(RequestParams params) {
        Map<String, String> stringMap = new HashMap<String, String>();
        if (params != null) {
            List<RequestParams.ValueWrapper> headerList = params.getParamsList();
            for (RequestParams.ValueWrapper header : headerList) {
                stringMap.put(header.key, header.value);
            }
        }
        return stringMap;
    }

    @Override
    public void get(Object tag, String url, HttpResponseCallback callback) {
        get(tag, url, null, callback);
    }

    @Override
    public void get(Object tag, String url, RequestParams params, HttpResponseCallback callback) {
        get(tag, url, params, null, callback);
    }

    @Override
    public void get(Object tag, String url, RequestParams params, RequestHeaders headers, HttpResponseCallback callback) {
        handleAsyncResult(Request.Method.GET, tag, url, params, headers, callback);
    }

    @Override
    public void post(Object tag, String url, HttpResponseCallback callback) {

    }

    @Override
    public void post(Object tag, String url, String data, HttpResponseCallback callback) {

    }

    @Override
    public void post(Object tag, String url, String data, String contentType, HttpResponseCallback callback) {

    }

    @Override
    public void post(Object tag, String url, String data, String contentType, RequestHeaders headers, HttpResponseCallback callback) {

    }

    @Override
    public void post(Object tag, String url, RequestParams params, HttpResponseCallback callback) {
        post(tag, url, params, null, callback);
    }

    @Override
    public void post(Object tag, String url, RequestParams params, RequestHeaders headers, HttpResponseCallback callback) {
        handleAsyncResult(Request.Method.POST, tag, url, params, headers, callback);
    }

    @Override
    public void syncGet(Object tag, String url, RequestParams params, RequestHeaders headers, HttpResponseCallback callback) {

    }

    @Override
    public void syncPost(Object tag, String url, RequestParams params, RequestHeaders headers, HttpResponseCallback callback) {

    }

    @Override
    public void cancel(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

    @Override
    public void cancelAll() {
        mRequestQueue.cancelAll("");
    }
}
