package com.lgmshare.component.http;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求参数构造类
 * <p/>
 * Created by LGM.
 * Datetime: 2016/5/7.
 * Email: lgmshare@mgail.com
 */
public class RequestParams {

    protected final static String LOG_TAG = "RequestParams";

    protected final ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<String, String>();
    protected final ConcurrentHashMap<String, FileWrapper> fileParams = new ConcurrentHashMap<String, FileWrapper>();
    protected final ConcurrentHashMap<String, Object> urlParamsWithObjects = new ConcurrentHashMap<String, Object>();

    protected String contentEncoding = "UTF_8";

    public void setContentEncoding(final String encoding) {
        if (encoding != null) {
            this.contentEncoding = encoding;
        } else {
            Log.d(LOG_TAG, "setContentEncoding called with null attribute");
        }
    }

    /**
     * Constructs a new empty {@code RequestParams} instance.
     */
    public RequestParams() {
        this(null);
    }

    /**
     * Constructs a new RequestParams instance containing the key/value string params from the
     * specified map.
     *
     * @param source the source key/value string map to addItem.
     */
    public RequestParams(Map<String, String> source) {
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Adds a file to the request.
     *
     * @param key  the key name for the new param.
     * @param file the file to addItem.
     * @throws FileNotFoundException throws if wrong File argument was passed
     */
    public void put(String key, File file) throws FileNotFoundException {
        put(key, null, file, null);
    }

    /**
     * Adds a file to the request with custom provided file content-type
     *
     * @param key         the key name for the new param.
     * @param file        the file to addItem.
     * @param contentType the content type of the file, eg. application/json
     * @throws FileNotFoundException throws if wrong File argument was passed
     */
    public void put(String key, File file, String contentType) throws FileNotFoundException {
        put(key, null, file, contentType);
    }

    /**
     * Adds a file to the request with custom provided file name
     *
     * @param key            the key name for the new param.
     * @param file           the file to addItem.
     * @param customFileName file name to use instead of real file name
     * @throws FileNotFoundException throws if wrong File argument was passed
     */
    public void put(String key, String customFileName, File file) throws FileNotFoundException {
        put(key, customFileName, file, null);
    }

    /**
     * Adds a file to the request with both custom provided file content-type and file name
     *
     * @param key            the key name for the new param.
     * @param file           the file to addItem.
     * @param contentType    the content type of the file, eg. application/json
     * @param customFileName file name to use instead of real file name
     * @throws FileNotFoundException throws if wrong File argument was passed
     */
    public void put(String key, String customFileName, File file, String contentType) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }
        if (customFileName == null) {
            customFileName = file.getName();
        }
        if (key != null) {
            fileParams.put(key, new FileWrapper(key, file, contentType, customFileName));
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
            urlParams.put(key, value);
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
            urlParams.put(key, String.valueOf(value));
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
            urlParams.put(key, String.valueOf(value));
        }
    }

    /**
     * Adds param with non-string value (e.g. Map, List, Set).
     *
     * @param key   the key name for the new param.
     * @param value the non-string value object for the new param.
     */
    public void put(String key, Object value) {
        if (key != null && value != null) {
            urlParamsWithObjects.put(key, value);
        }
    }

    /**
     * Adds string value to param which can have more than one value.
     *
     * @param key   the key name for the param, either existing or new.
     * @param value the value string for the new param.
     */
    public void add(String key, String value) {
        if (key != null && value != null) {
            Object params = urlParamsWithObjects.get(key);
            if (params == null) {
                // Backward compatible, which will result in "k=v1&k=v2&k=v3"
                params = new HashSet<String>();
                this.put(key, params);
            }
            if (params instanceof List) {
                ((List<Object>) params).add(value);
            } else if (params instanceof Set) {
                ((Set<Object>) params).add(value);
            }
        }
    }

    /**
     * Removes a parameter from the request.
     *
     * @param key the key name for the parameter to removeItem.
     */
    public void remove(String key) {
        urlParams.remove(key);
        fileParams.remove(key);
        urlParamsWithObjects.remove(key);
    }

    /**
     * Check if a parameter is defined.
     *
     * @param key the key name for the parameter to check existence.
     * @return Boolean
     */
    public boolean has(String key) {
        return urlParams.get(key) != null ||
                fileParams.get(key) != null ||
                urlParamsWithObjects.get(key) != null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }

        for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append("FILE");
        }

        return result.toString();
    }

    public String getParamString() {
        return Utils.format(getParamsList(), contentEncoding);
    }

    public List<FileWrapper> getFileParamsList() {
        List<FileWrapper> lparams = new LinkedList<FileWrapper>();

        for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
            lparams.add(entry.getValue());
        }

        return lparams;
    }

    public List<ValueWrapper> getParamsList() {
        List<ValueWrapper> lparams = new LinkedList<ValueWrapper>();

        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            lparams.add(new ValueWrapper(entry.getKey(), entry.getValue()));
        }

        lparams.addAll(getParamsList(null, urlParamsWithObjects));

        return lparams;
    }

    private List<ValueWrapper> getParamsList(String key, Object value) {
        List<ValueWrapper> params = new LinkedList<ValueWrapper>();
        if (value instanceof Map) {
            Map map = (Map) value;
            List list = new ArrayList<Object>(map.keySet());
            // Ensure consistent ordering in query string
            if (list.size() > 0 && list.get(0) instanceof Comparable) {
                Collections.sort(list);
            }
            for (Object nestedKey : list) {
                if (nestedKey instanceof String) {
                    Object nestedValue = map.get(nestedKey);
                    if (nestedValue != null) {
                        params.addAll(getParamsList(key == null ? (String) nestedKey : String.format(Locale.US, "%s[%s]", key, nestedKey),
                                nestedValue));
                    }
                }
            }
        } else if (value instanceof List) {
            List list = (List) value;
            int listSize = list.size();
            for (int nestedValueIndex = 0; nestedValueIndex < listSize; nestedValueIndex++) {
                params.addAll(getParamsList(String.format(Locale.US, "%s[%d]", key, nestedValueIndex), list.get(nestedValueIndex)));
            }
        } else if (value instanceof Object[]) {
            Object[] array = (Object[]) value;
            int arrayLength = array.length;
            for (int nestedValueIndex = 0; nestedValueIndex < arrayLength; nestedValueIndex++) {
                params.addAll(getParamsList(String.format(Locale.US, "%s[%d]", key, nestedValueIndex), array[nestedValueIndex]));
            }
        } else if (value instanceof Set) {
            Set set = (Set) value;
            for (Object nestedValue : set) {
                params.addAll(getParamsList(key, nestedValue));
            }
        } else {
            params.add(new ValueWrapper(key, value.toString()));
        }
        return params;
    }

    public static class ValueWrapper implements Serializable {
        public final String key;
        public final String value;

        public ValueWrapper(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public static class FileWrapper implements Serializable {
        public final String key;
        public final File file;
        public final String contentType;
        public final String customFileName;

        public FileWrapper(String key, File file, String contentType, String customFileName) {
            this.key = key;
            this.file = file;
            this.contentType = contentType;
            this.customFileName = customFileName;
        }
    }

}
