package com.lgmshare.base.http.task;

import com.alibaba.fastjson.JSON;
import com.lgmshare.base.http.HttpRequestURL;
import com.lgmshare.base.model.User;
import com.lgmshare.component.utils.SecurityUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author: lim
 * @description: TODO
 * @email: lgmshare@gmail.com
 * @datetime 2015/12/30
 */
public class UserLoginTask extends HttpRequestTask<User> {

    public UserLoginTask(String username, String password, String udid) {
        JSONObject requestParams = getRequestParam();
        try {
            requestParams.put("username", username);
            requestParams.put("pwd", SecurityUtil.encryptMD5(password));
            requestParams.put("udid", udid);
            requestParams.put("reqName", HttpRequestURL.A1);
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
    public User parse(String data) {
        return JSON.parseObject(data, User.class);
    }
}
