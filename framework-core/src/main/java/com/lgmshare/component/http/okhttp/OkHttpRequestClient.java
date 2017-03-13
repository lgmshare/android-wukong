package com.lgmshare.component.http.okhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.lgmshare.component.http.HttpConfiguration;
import com.lgmshare.component.http.HttpRequest;
import com.lgmshare.component.http.HttpRequestClient;
import com.lgmshare.component.http.HttpResponseCallback;
import com.lgmshare.component.http.RequestHeaders;
import com.lgmshare.component.http.RequestParams;
import com.lgmshare.component.http.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/5/9 11:33
 */
public class OkHttpRequestClient implements HttpRequest {

    private static final Object TAG = "OkHttpRequestClient";

    private static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain; charset=utf-8");
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    private static final String CONTENT_TYPE_FILE = "application/octet-stream; charset=UTF-8";

    private OkHttpClient mOkHttpClient;
    private Handler mUIHandler;

    public OkHttpRequestClient(Context context, HttpConfiguration configuration) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(configuration.getConnectTimeout(), TimeUnit.SECONDS);
        builder.writeTimeout(HttpRequestClient.CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(HttpRequestClient.CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.cache(new Cache(context.getCacheDir(), 3 * 1024 * 2014));
        builder.cookieJar(new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put("cookies", cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get("cookies");
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });
        //builder.addInterceptor(new GzipInterceptor());
        //builder.addInterceptor(new HttpCacheInterceptor());

        mOkHttpClient = builder.build();

        mUIHandler = new Handler(Looper.getMainLooper());
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
        final Request request = buildGetRequest(tag, url, params, headers);
        handleAsyncResult(request, callback);
    }

    @Override
    public void post(Object tag, String url, HttpResponseCallback callback) {
        post(tag, url, null, null, null, callback);
    }

    @Override
    public void post(Object tag, String url, String data, HttpResponseCallback callback) {
        post(tag, url, data, null, null, callback);
    }

    @Override
    public void post(Object tag, String url, String data, String contentType, HttpResponseCallback callback) {
        post(tag, url, data, contentType, null, callback);
    }

    @Override
    public void post(Object tag, String url, String data, String contentType, RequestHeaders headers, HttpResponseCallback callback) {
        final Request request = buildPostRequestBody(tag, url, headers, data, contentType);
        handleAsyncResult(request, callback);
    }

    @Override
    public void post(Object tag, String url, RequestParams params, HttpResponseCallback callback) {
        post(tag, url, params, null, callback);
    }

    @Override
    public void post(Object tag, String url, RequestParams params, RequestHeaders headers, HttpResponseCallback callback) {
        final Request request = build(tag, url, params, headers, callback);
        handleAsyncResult(request, callback);
    }

    @Override
    public void syncGet(Object tag, String url, RequestParams params, RequestHeaders headers, HttpResponseCallback callback) {
        Response response = null;
        try {
            final Request request = buildGetRequest(tag, url, params, headers);
            final Call call = mOkHttpClient.newCall(request);
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        handleSyncResult(response, callback);
    }

    @Override
    public void syncPost(Object tag, String url, RequestParams params, RequestHeaders headers, HttpResponseCallback callback) {
        Response response = null;
        try {
            Request request = build(tag, url, params, headers, callback);
            response = mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        handleSyncResult(response, callback);
    }

    @Override
    public void cancel(Object tag) {
        List<Call> queuedCalls = mOkHttpClient.dispatcher().queuedCalls();
        List<Call> runningCalls = mOkHttpClient.dispatcher().runningCalls();
        for (Call call : queuedCalls) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : runningCalls) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    @Override
    public void cancelAll() {
        mOkHttpClient.dispatcher().cancelAll();
    }

    /**
     * 构建header
     *
     * @param headerList
     * @return
     */
    private Headers buildHeaders(RequestHeaders headerList) {
        Headers.Builder builder = new Headers.Builder();
        if (headerList != null) {
            List<RequestHeaders.ValueWrapper> list = headerList.getHeaderList();
            if (list != null && list.size() > 0) {
                for (RequestHeaders.ValueWrapper header : list) {
                    builder.add(header.key, header.value);
                }
            }
        }
        return builder.build();
    }

    /**
     * 构建post请求-文本格式
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */
    private Request buildGetRequest(Object tag, String url, RequestParams params, RequestHeaders headers) {
        Headers header = buildHeaders(headers);
        return new Request.Builder()
                .tag(tag)
                .url(Utils.getUrlWithQueryString(false, url, params))
                .headers(header)
                .build();
    }

    private Request build(Object tag, String url, RequestParams params, RequestHeaders headers, HttpResponseCallback callback) {
        if (params.getFileParamsList() != null && params.getFileParamsList().size() > 0) {
            return buildPostRequestMultipart(tag, url, params, headers, callback);
        } else {
            return buildPostRequestFrom(tag, url, params, headers);
        }
    }

    /**
     * 构建post请求-文本格式
     *
     * @param url
     * @param headers
     * @param postBody
     * @return
     */
    private Request buildPostRequestBody(Object tag, String url, RequestHeaders headers, String postBody, String contentType) {
        MediaType mediaType = MEDIA_TYPE_PLAIN;
        if (!TextUtils.isEmpty(contentType)) {
            mediaType = MediaType.parse(contentType);
        }
        Headers header = buildHeaders(headers);
        Request.Builder builder = new Request.Builder();
        builder.tag(tag);
        builder.url(url);
        builder.headers(header);
        if (postBody != null) {
            builder.post(RequestBody.create(mediaType, postBody));
        }
        return builder.build();
    }

    /**
     * 构建post请求-表单格式
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */
    private Request buildPostRequestFrom(Object tag, String url, RequestParams params, RequestHeaders headers) {
        FormBody.Builder builder = new FormBody.Builder();
        List<RequestParams.ValueWrapper> paramsList = params.getParamsList();
        if (paramsList != null && paramsList.size() > 0) {
            for (RequestParams.ValueWrapper wrapper : paramsList) {
                builder.add(wrapper.key, wrapper.value);
            }
        }
        Headers header = buildHeaders(headers);
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .tag(tag)
                .url(url)
                .headers(header)
                .post(requestBody)
                .build();
    }

    /**
     * 构建post请求-文件分段格式
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */
    private Request buildPostRequestMultipart(Object tag, String url, RequestParams params, RequestHeaders headers, final HttpResponseCallback callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //文本
        List<RequestParams.ValueWrapper> paramsList = params.getParamsList();
        if (paramsList != null && paramsList.size() > 0) {
            for (RequestParams.ValueWrapper wrapper : paramsList) {
                builder.addFormDataPart(wrapper.key, wrapper.value);
            }
        }
        //文件
        List<RequestParams.FileWrapper> fileParamsList = params.getFileParamsList();
        if (fileParamsList != null && fileParamsList.size() > 0) {
            for (RequestParams.FileWrapper wrapper : fileParamsList) {
                MediaType mediaType;
                if (TextUtils.isEmpty(wrapper.contentType)) {
                    mediaType = MediaType.parse(CONTENT_TYPE_FILE);
                } else {
                    mediaType = MediaType.parse(wrapper.contentType);
                }
                builder.addFormDataPart(wrapper.key, wrapper.customFileName, RequestBody.create(mediaType, wrapper.file));
            }
        }
        Headers header = buildHeaders(headers);

        CountingRequestBody monitoredRequest = new CountingRequestBody(builder.build(), new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(long contentLength, long bytesWritten, long offset) {
                sendProgressCallback(contentLength, bytesWritten, offset, callback);
            }
        });

        return new Request.Builder()
                .url(url)
                .tag(tag)
                .headers(header)
                .post(monitoredRequest)
                .build();
    }

    /**
     * 同步请求结果处理
     *
     * @param response
     * @param callback
     */
    private void handleSyncResult(Response response, final HttpResponseCallback callback) {
        try {
            final String data = response.body().string();
            final int stateCode = response.code();
            if (HttpRequestClient.HTTP_OK == stateCode) {
                sendSuccessResultCallback(data, callback);
            } else {
                sendFailedStringCallback(stateCode, new IOException("Unexpected code" + response), callback);
            }
        } catch (IOException e) {
            sendFailedStringCallback(HttpRequestClient.HTTP_UNEXPECTED_CODE, e, callback);
        }
    }

    /**
     * 异步请求结果处理
     *
     * @param request
     * @param callback
     */
    private void handleAsyncResult(final Request request, final HttpResponseCallback callback) {
        if (callback != null) {
            callback.onStart();
        }
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, final IOException e) {
                if (call.isCanceled()) {
                    //请求取消
                    sendCancelResultCallback(callback);
                    return;
                }
                sendFailedStringCallback(HttpRequestClient.HTTP_UNEXPECTED_CODE, e, callback);
            }

            @Override
            public void onResponse(Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        //请求取消
                        sendCancelResultCallback(callback);
                        return;
                    }

                    final String data = response.body().string();
                    final int stateCode = response.code();
                    if (HttpRequestClient.HTTP_OK == stateCode) {
                        sendSuccessResultCallback(data, callback);
                    } else {
                        sendFailedStringCallback(stateCode, new IOException("Unexpected code" + response), callback);
                    }

                } catch (IOException e) {
                    sendFailedStringCallback(HttpRequestClient.HTTP_UNEXPECTED_CODE, e, callback);
                }
            }
        });
    }

    /**
     * 切换到UI线程响应
     *
     * @param data
     * @param callback
     */
    private void sendSuccessResultCallback(final String data, final HttpResponseCallback callback) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFinish();
                    callback.onSuccess(HttpRequestClient.HTTP_OK, data);
                }
            }
        });
    }

    /**
     * 切换到UI线程响应
     *
     * @param stateCode
     * @param e
     * @param callback
     */
    private void sendFailedStringCallback(final int stateCode, final Exception e, final HttpResponseCallback callback) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFinish();
                    callback.onFailure(stateCode, e.getMessage(), e);
                }
            }
        });
    }

    /**
     * 切换到UI线程响应
     *
     * @param callback
     */
    private void sendCancelResultCallback(final HttpResponseCallback callback) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFinish();
                    callback.onCancel();
                }
            }
        });
    }

    /**
     * 更新到UI线程响应
     *
     * @param total
     * @param offset
     * @param callback
     */
    private void sendProgressCallback(final long total, final long process, final long offset, final HttpResponseCallback callback) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onProgress(total, process, offset);
                }
            }
        });
    }
}
