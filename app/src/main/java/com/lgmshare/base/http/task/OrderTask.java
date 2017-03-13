package com.lgmshare.base.http.task;

import com.alibaba.fastjson.JSON;
import com.lgmshare.base.http.HttpRequestURL;
import com.lgmshare.base.model.ListGroup;
import com.lgmshare.base.model.Order;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/2/14 13:53
 */
public class OrderTask extends HttpRequestTask<ListGroup<Order>> {

    public OrderTask(int pageIndex, String cityName) {
        JSONObject requestParams = getRequestParam();
        try {
            requestParams.put("pagesize", 10);
            requestParams.put("pageindex", String.valueOf(pageIndex));
            requestParams.put("city", cityName);
            requestParams.put("reqName", HttpRequestURL.E5);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String httptUrl() {
        return HttpRequestURL.URL_E;
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
    public ListGroup<Order> parse(String data) {
        ListGroup<Order> lg = new ListGroup<Order>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.has("data")){
                lg.setList(JSON.parseArray(jsonObject.getString("data"), Order.class));
            }
            lg.setTotalSize(jsonObject.getInt("sumCount"));
            lg.setPageTotal(jsonObject.getInt("pagecount"));
            lg.setPageIndex(jsonObject.getInt("pageindex"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lg;
    }
}
