package com.lgmshare.base.http.task;

import com.alibaba.fastjson.JSON;
import com.lgmshare.base.http.HttpRequestURL;
import com.lgmshare.base.model.Advertisement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lim
 * @description: TODO
 * @email: lgmshare@gmail.com
 * @datetime 2016/2/1
 */
public class AdvertisementTask extends HttpRequestTask<List<Advertisement>> {

    @Override
    public String httptUrl() {
        return HttpRequestURL.URL_A1 + HttpRequestURL.A1;
    }

    @Override
    public int httpMethod() {
        return Method.POST;
    }

    @Override
    protected boolean debug() {
        return true;
    }

    @Override
    protected String debugData() {
        JSONObject object = new JSONObject();
        try {
            List<Advertisement> list = new ArrayList<Advertisement>();
            for (int i = 0; i < 5; i++) {
                Advertisement adv = new Advertisement();
                adv.setImage("http://h.hiphotos.baidu.com/image/pic/item/fd039245d688d43ff852eaf97f1ed21b0ff43bc5.jpg");
                adv.setUrl("http://www.baidu.com");
                list.add(adv);
            }
            object.put("success", "200");
            object.put("message", "success");
            object.put("data", JSON.toJSONString(list));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    @Override
    public List<Advertisement> parse(String data) {
        return JSON.parseArray(data, Advertisement.class);
    }
}
