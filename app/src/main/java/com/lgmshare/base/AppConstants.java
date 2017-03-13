package com.lgmshare.base;

/**
 * 应用常量Key
 * <p/>
 * Created by Administrator on 2015/6/12.
 */
public class AppConstants {

    /**
     * IntentKey
     */
    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_FROM = "FROM";
    public static final String EXTRA_USER = "USER";
    public static final String EXTRA_USER_ID = "USER_ID";
    public static final String EXTRA_IMAGE_PATH = "image_path";
    public static final String EXTRA_IMAGE_CROP = "image_crop";
    public static final String EXTRA_WEB_TITLE = "web_title";
    public static final String EXTRA_WEB_URL = "web_url";

    /**
     * IntentFilterKey
     */
    public final static String INTENT_ACTION_ARCHIVE = "com.todotxt.todotxttouch.ACTION_ARCHIVE";
    public final static String INTENT_ACTION_LOGOUT = "com.todotxt.todotxttouch.ACTION_LOGOUT";
    public final static String INTENT_ASYNC_SUCCESS = "com.todotxt.todotxttouch.ASYNC_SUCCESS";
    public final static String INTENT_ASYNC_FAILED = "com.todotxt.todotxttouch.ASYNC_FAILED";

    /**
     * NoticationKey
     */
    public static final String NOTIFICATION_LAST_ACTIONED_MENTION_ID = "notification_last_actioned_mention_id_v1_";
    public static final String NOTIFICATION_LAST_DISPLAYED_MENTION_ID = "notification_last_displayed_mention_id_v1_";
    public static final String NOTIFICATION_COUNT = "notification_count_";
    public static final String NOTIFICATION_SUMMARY = "notification_summary_";
    public static final String NOTIFICATION_LAST_ACTIONED_DIRECT_MESSAGE_ID = "notification_last_actioned_direct_message_id_v1_";
    public static final String NOTIFICATION_LAST_DISPLAYED_DIRECT_MESSAGE_ID = "notification_last_displayed_direct_message_id_v1_";
    public static final String NOTIFICATION_TYPE_MENTION = "_mention";
    public static final String NOTIFICATION_TYPE_DIRECT_MESSAGE = "_directmessage";

    /**
     * PreferenceKey
     */
    public static final String PREFERENCE_KEY_APP_UNIQUEID = "preference_app_uniqueid";
    public static final String PREFERENCE_KEY_APP_VERSION_CODE = "preference_app_version_code";
    public static final String PREFERENCE_KEY_THEME = "preference_theme";
    public static final String PREFERENCE_KEY_INITIALIZED = "preference_initialized";
    public static final String PREFERENCE_KEY_RINGTONE = "preference_ringtone";
    public static final String PREFERENCE_KEY_NOTIFICATION_TIME = "preference_notification_time";
    public static final String PREFERENCE_KEY_NOTIFICATION_TYPE = "preference_notification_type";
    public static final String PREFERENCE_KEY_NOTIFICATION_VIBRATION = "preference_notification_vibration";
    public static final String PREFERENCE_KEY_AUTO_REFRESH = "preference_auto_refresh";
    public static final String PREFERENCE_KEY_DISPLAY_URL = "preference_display_url";
}
