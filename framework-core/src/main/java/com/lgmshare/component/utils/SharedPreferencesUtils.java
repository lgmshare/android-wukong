package com.lgmshare.component.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Set;

/**
 * SharedPreferences文件数据保存
 *
 * @author: lim
 * @description: SharedPreferences文件数据保存
 * @email: lgmshare@gmail.com
 * @datetime 2014年6月9日 下午5:13:33
 */
public class SharedPreferencesUtils {

    private SharedPreferences mSharedPreferences;

    private SharedPreferencesUtils(Context context) {
        mSharedPreferences = getSharedPreferences(context, getDefaultSharedPreferencesName(context));
    }

    public static SharedPreferencesUtils getInstance(Context context) {
        return new SharedPreferencesUtils(context);
    }

    private SharedPreferences getSharedPreferences(Context context, String preferencesFile) {
        return context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE);
    }

    private String getDefaultSharedPreferencesName(Context context) {
        String packageName = context.getPackageName();
        return packageName.replace(".", "_").concat("_preferences");
    }

    public void putInt(String key, int value) {
        Editor editor = getEditor();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putString(String key, String value) {
        Editor editor = getEditor();
        editor.putString(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        Editor editor = getEditor();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putFloat(String key, float value) {
        Editor editor = getEditor();
        editor.putFloat(key, value);
        editor.commit();
    }

    public void putLong(String key, long value) {
        Editor editor = getEditor();
        editor.putLong(key, value);
        editor.commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void putSetString(String key, Set<String> values) {
        Editor editor = getEditor();
        editor.putStringSet(key, values);
        editor.commit();
    }

    /**
     * 保存序列化对象
     *
     * @param key
     * @param object
     * @throws IOException
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public void putObject(String key, Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        // 将对象放到OutputStream中
        oos.writeObject(object);
        // 将对象转换成byte数组，并将其进行base64编码
        String productBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        Editor editor = getEditor();
        // 将编码后的字符串写到base64.xml文件中
        editor.putString(key, productBase64);
        editor.commit();
    }

    public int getInt(String key) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getInt(key, 0);
    }

    public int getInt(String key, int defaultVal) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getInt(key, defaultVal);
    }

    public String getString(String key) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getString(key, "");
    }

    public String getString(String key, String defaultVal) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getString(key, defaultVal);
    }

    public boolean getBoolean(String key) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getBoolean(key, false);
    }

    public boolean getBooleanValue(String key, boolean defaultVal) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getBoolean(key, defaultVal);
    }

    public float getFloat(String key) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getFloat(key, 0);
    }

    public float getFloat(String key, float defaultVal) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getFloat(key, defaultVal);
    }

    public long getLong(String key) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getLong(key, 0);
    }

    public long getLong(String key, long defaultVal) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getLong(key, defaultVal);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(String key, Set<String> defaultVal) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getStringSet(key, defaultVal);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(String key) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getStringSet(key, null);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public Object getObject(String key) throws IOException, ClassNotFoundException {
        SharedPreferences preferences = getSharedPreferences();
        String productBase64 = preferences.getString(key, "");
        if (productBase64.isEmpty()) {
            return null;
        }
        // 对Base64格式的字符串进行解码
        byte[] base64Bytes = Base64.decode(productBase64, Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        // 从ObjectInputStream中读取对象
        return ois.readObject();
    }

    public Map<String, ?> getAll(Context context) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getAll();
    }

    /**
     * 删除keyValue
     *
     * @param key
     */
    public void remove(String key) {
        Editor editor = getEditor();
        editor.remove(key);
        editor.commit();
    }

    public void remove(String... keys) {
        Editor editor = getEditor();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.commit();
    }

    /**
     * 清空数据
     */
    public void clear() {
        Editor editor = getEditor();
        editor.clear();
        editor.commit();
    }

    private Editor getEditor() {
        SharedPreferences preferences = getSharedPreferences();
        Editor editor = preferences.edit();
        return editor;
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

}