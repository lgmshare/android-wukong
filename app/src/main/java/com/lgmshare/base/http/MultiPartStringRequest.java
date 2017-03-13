package com.lgmshare.base.http;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.lgmshare.component.logger.Logger;
import com.lgmshare.component.network.volley.RequestParams;
import com.lgmshare.component.network.volley.ResponseCallback;
import com.lgmshare.component.network.volley.ResponseParser;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MultiPartStringRequest<T> extends Request<T> {

    /**
     * 调试TAG
     */
    private static final String TAG = MultiPartStringRequest.class.getSimpleName();

    public static final String CONTENT_TYPE_IMG = "image/png; charset=UTF-8";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_RANGE = "Content-Range";
    public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String ENCODING_GZIP = "gzip";

    private ResponseCallback<T> mCallback;
    private ResponseParser<T> mParser;

    private Map<String, String> mHeaders = new HashMap<String, String>();
    private StringEntity mEntity;

    public MultiPartStringRequest(int method, String url, String params, ResponseParser<T> parser, ResponseCallback<T> callback) {
        super(method, url, callback);
        mCallback = callback;
        mParser = parser;
        try {
            mEntity = new StringEntity(params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private HttpEntity paramsToEntity(RequestParams params) {
        HttpEntity entity = null;
        try {
            if (params != null) {
                entity = params.getEntity();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    public void setHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (mEntity != null) {
            try {
                mEntity.writeTo(baos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }

    @Override
    protected void deliverResponse(T t) {
        if (mCallback != null)
            mCallback.onResponse(t);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Logger.d(TAG, data);
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.has("success")) {
                String code = jsonObject.getString("success");
                if (code.equals("true")) {
                    return Response.success(mParser.parse(data), HttpHeaderParser.parseCacheHeaders(response));
                } else {
                    return Response.error(new VolleyError(jsonObject.optString("msg")));
                }
            }
            return Response.error(new VolleyError(data));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new VolleyError(e));
        } catch (JSONException e) {
            return Response.error(new VolleyError("json format exceition", e));
        }
    }
}
