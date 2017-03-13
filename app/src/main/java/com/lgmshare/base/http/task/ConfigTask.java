package com.lgmshare.base.http.task;

import com.alibaba.fastjson.JSON;
import com.lgmshare.base.http.HttpRequestURL;
import com.lgmshare.base.model.User2;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author: lim
 * @description: TODO
 * @email: lgmshare@gmail.com
 * @datetime 2015/12/30
 */
public class ConfigTask extends HttpRequestTask<User2> {

    public ConfigTask() {
        JSONObject requestParams = getRequestParam();
        try {
            requestParams.put("reqName", HttpRequestURL.A7);
            requestParams.put("pdatype", HttpRequestURL.INTERFACE_PDATYPE);
            requestParams.put("version", HttpRequestURL.INTERFACE_VERSION);
            requestParams.put("lon", "0.0");
            requestParams.put("lat", "0.0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String httptUrl() {
        return HttpRequestURL.URL_A1;
    }

    @Override
    public int httpMethod() {
        return Method.POST;
    }

    @Override
    protected boolean debug() {
        return false;
    }

    @Override
    protected String debugData() {
        return null;
    }

    @Override
    public User2 parse(String data) {
        return JSON.parseObject(data, User2.class);
    }
}
