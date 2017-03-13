package com.lgmshare.component.http.volley;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by LGM.
 * Datetime: 2016/5/7.
 * Email: lgmshare@mgail.com
 */
public abstract class VolleyResponseCallback implements Response.Listener<String>, Response.ErrorListener {

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }
}
