package com.lgmshare.component.http.volley;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class StringRequest extends Request<String> {

    /**
     * 调试TAG
     */
    private static final String TAG = "StringRequest";

    private Map<String, String> mHeaders = new HashMap<String, String>();
    private Map<String, String> mStringParams = new HashMap<String, String>();

    private final Response.Listener<String> mListener;

    public StringRequest(int method, String url, VolleyResponseCallback listener) {
        super(method, url, listener);
        mListener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mStringParams;
    }

    public void setHeaders(Map<String, String> map) {
        mHeaders.putAll(map);
    }

    public void setParams(Map<String, String> params) {
        mStringParams.putAll(params);
    }

    public void addParam(String key, String value) {
        mStringParams.put(key, value);
    }

    @Override
    protected void deliverResponse(String t) {
        if (mListener != null) {
            mListener.onResponse(t);
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String data;
        try {
            data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            data = new String(response.data);
        }
        return Response.success(data, HttpHeaderParser.parseCacheHeaders(response));
    }
}
