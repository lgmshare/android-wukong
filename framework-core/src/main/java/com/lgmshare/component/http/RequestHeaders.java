package com.lgmshare.component.http;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求header构造类
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/5/9 11:18
 */
public class RequestHeaders {

    protected final ConcurrentHashMap<String, String> headerParams = new ConcurrentHashMap<String, String>();

    /**
     * Constructs a new empty {@code RequestParams} instance.
     */
    public RequestHeaders() {
        this(null);
    }

    /**
     * Constructs a new RequestHeaders instance containing the key/value string params from the
     * specified map.
     *
     * @param source the source key/value string map to addItem.
     */
    public RequestHeaders(Map<String, String> source) {
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Adds a key/value string pair to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value string for the new param.
     */
    public void put(String key, String value) {
        if (key != null && value != null) {
            headerParams.put(key, value);
        }
    }


    /**
     * Adds a int value to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value int for the new param.
     */
    public void put(String key, int value) {
        if (key != null) {
            headerParams.put(key, String.valueOf(value));
        }
    }

    /**
     * Adds a long value to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value long for the new param.
     */
    public void put(String key, long value) {
        if (key != null) {
            headerParams.put(key, String.valueOf(value));
        }
    }

    public List<ValueWrapper> getHeaderList() {
        List<ValueWrapper> lparams = new LinkedList<ValueWrapper>();

        for (ConcurrentHashMap.Entry<String, String> entry : headerParams.entrySet()) {
            lparams.add(new ValueWrapper(entry.getKey(), entry.getValue()));
        }

        return lparams;
    }

    public static class ValueWrapper implements Serializable {
        public final String key;
        public final String value;

        public ValueWrapper(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
