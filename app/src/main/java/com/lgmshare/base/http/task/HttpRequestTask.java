package com.lgmshare.base.http.task;


import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.lgmshare.base.http.BaseResponseParser;
import com.lgmshare.base.http.HttpRequestURL;
import com.lgmshare.base.http.HttpResponseHandler;
import com.lgmshare.component.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class HttpRequestTask<T> implements BaseResponseParser<T> {

    private final String TAG = HttpRequestTask.class.getSimpleName();

    /**
     * The default socket timeout in milliseconds
     */
    public static final int DEFAULT_TIMEOUT_MS = 30 * 1000;
    /**
     * The default number of retries
     */
    public static final int DEFAULT_MAX_RETRIES = 1;
    /**
     * The default backoff multiplier
     */
    public static final float DEFAULT_BACKOFF_MULT = 1f;

    interface Method {
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int PATCH = 4;
    }

    private Request mRequest;
    private JSONObject requestParams = new JSONObject();
    private com.lgmshare.base.http.HttpResponseHandler mHttpResponseHandler;

    public void setHttpResponseHandler(com.lgmshare.base.http.HttpResponseHandler handler) {
        mHttpResponseHandler = handler;
    }

    public void send(final Context tag) {
        HttpResponseHandler<T> handler = new HttpResponseHandler<T>() {

            @Override
            public void onSuccess(T t) {
                if (mHttpResponseHandler != null) {
                    mHttpResponseHandler.onSuccess(t);
                }
            }

            @Override
            public void onFailure(int code, String error) {
                if (mHttpResponseHandler != null) {
                    mHttpResponseHandler.onFailure(code, error);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (mHttpResponseHandler != null) {
                    mHttpResponseHandler.onFinish();
                }
            }
        };

        if (mHttpResponseHandler != null) {
            mHttpResponseHandler.onStart();
        }

        if (debug()) {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(debugData());
                        if (jsonObject.has("success")) {
                            String code = jsonObject.getString("success");
                            if (code.equals("200")) {
                                mHttpResponseHandler.onSuccess(parse(jsonObject.getString("data")));
                            } else {
                                mHttpResponseHandler.onFailure(200, jsonObject.getString("message"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mHttpResponseHandler.onFailure(200, new VolleyError(e).getMessage());
                    }
                    mHttpResponseHandler.onFinish();
                }
            }.sendEmptyMessageDelayed(0, 3000);
            return;
        }

        Logger.d("MultiPartStringRequest", requestParams.toString());
        try {
            requestParams.put("pdatype", HttpRequestURL.INTERFACE_PDATYPE);
            requestParams.put("version", HttpRequestURL.INTERFACE_VERSION);
            requestParams.put("lon", "104.058093");
            requestParams.put("lat", "30.659066");
        } catch (JSONException e) {
            e.printStackTrace();
        }
       /* Map<String, String> headers = HttpRequestURL.createCommonHeader();
        String params = HttpRequestURL.getSecretParams(headers.get("timestamp"), requestParams.toString());
        HttpRequestClient.getInstance().post(tag, httptUrl(), params,  headers, new HttpResponseCallback() {
            @Override
            public void onSuccess(int statusCode, String responseBody) {
                ToastUtil.show(tag, responseBody);
            }

            @Override
            public void onFailure(int statusCode, String responseBody, Throwable error) {
                ToastUtil.show(tag, responseBody);
            }
        });*/
        /*Logger.d("MultiPartStringRequest", params);
        MultiPartStringRequest<T> request = new MultiPartStringRequest(httpMethod(), httptUrl(), params, this, handler);
        request.setTag(tag);
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT));

        request.setHeader("UDID", headers.get("UDID"));
        request.setHeader("User-Agent", headers.get("User-Agent"));
        request.setHeader("timestamp", headers.get("timestamp"));
        request.setHeader("Accept", headers.get("Accept"));
        if (headers.size() > 4){
            request.setHeader("USERNAME", headers.get("USERNAME"));
            request.setHeader("TOKEN", headers.get("TOKEN"));
        }
        mRequest = request;

        RequestQueue requestQueue = AppContext.getRequestQueue();
        requestQueue.add(request);*/
    }

    public JSONObject getRequestParam() {
        return requestParams;
    }

    public void cancel() {
        mRequest.cancel();
        if (mHttpResponseHandler != null) {
            mHttpResponseHandler.onCancel();
        }
    }

    /**
     * 请求地址
     *
     * @return
     */
    public abstract String httptUrl();

    /**
     * 请求类别
     *
     * @return
     */
    public abstract int httpMethod();

    /**
     * 是否是调试模式
     *
     * @return
     */
    protected abstract boolean debug();

    /**
     * 调试数据
     *
     * @return
     */
    protected abstract String debugData();

}
