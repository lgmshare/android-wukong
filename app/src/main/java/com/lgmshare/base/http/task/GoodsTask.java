package com.lgmshare.base.http.task;

import com.alibaba.fastjson.JSON;
import com.lgmshare.base.http.HttpRequestURL;
import com.lgmshare.base.model.Goods;
import com.lgmshare.base.model.ListGroup;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/2/14 11:05
 */
public class GoodsTask extends HttpRequestTask<ListGroup<Goods>> {

    public GoodsTask() {
        JSONObject requestParams = getRequestParam();
        try {
            requestParams.put("reqName", HttpRequestURL.A7);
            requestParams.put("pdatype", HttpRequestURL.INTERFACE_PDATYPE);
            requestParams.put("version", HttpRequestURL.INTERFACE_VERSION);
            requestParams.put("lon", "104.058093");
            requestParams.put("lat", "30.659066");
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
    public ListGroup<Goods> parse(String data) {
        ListGroup<Goods> lg = new ListGroup<Goods>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            lg.setList(JSON.parseArray(jsonObject.getString("data"), Goods.class));
            lg.setTotalSize(jsonObject.getInt("tatle_size"));
            lg.setTotalSize(jsonObject.getInt("tatle_size"));
            lg.setTotalSize(jsonObject.getInt("tatle_size"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lg;
    }
}
