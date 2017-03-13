package com.lgmshare.base.util;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * JSON格式化工具
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/2/19 14:38
 */
public class JsonParseUtil {

    public static <T> T parseObject(String data, Class<T> clazz) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        return JSON.parseObject(data, clazz);
    }

    public static <T> List<T> parseArray(String data, Class<T> clazz) {
        return JSON.parseArray(data, clazz);
    }

    public static String toJSONString(Object obj) {
        return JSON.toJSONString(obj);
    }

}
