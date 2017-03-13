package com.lgmshare.base.helper;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.SparseIntArray;

import com.lgmshare.base.AppContext;
import com.lgmshare.base.R;
import com.lgmshare.component.utils.PreferenceUtil;


/**
 * 多媒体播放管理类
 *
 * @author: lim.
 * @email: lgmshare@gmail.com
 * @Version V1.0
 * @Description
 * @datetime : 2015/5/11 11:32
 */
public class MediaPlayHelper {

    public static final String KEY_NOTIFY_SOUND = "isNotifySound";
    public static final String KEY_NOTIFY_VIBRATE = "isNotifyVibrate";

    private static MediaPlayHelper mInstance;

    private Context mContext;
    private SoundPool mSoundPool;
    private SparseIntArray mSpMap;
    private boolean mEnableBusy = false;

    public static MediaPlayHelper getInstance() {
        if (mInstance == null) {
            mInstance = new MediaPlayHelper(AppContext.getContext());
        }
        return mInstance;
    }

    private MediaPlayHelper(Context context) {
        mContext = context;
        mSoundPool = new SoundPool(1, AudioManager.STREAM_RING, 100);
        mSpMap = new SparseIntArray();
        mSpMap.put(1, mSoundPool.load(context, R.raw.alert_deng, 1));
        mSpMap.put(2, mSoundPool.load(context, R.raw.alert_new_order, 1));

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // 创建一个监听对象，监听电话状态改变事件
        tm.listen(new InnerPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
    }

    /**
     * 声音播报
     */
    public void playNewSound() {
        boolean isbusy = mEnableBusy;
        boolean isPlay = PreferenceUtil.getInstance().getBooleanValue(KEY_NOTIFY_SOUND, true);
        if (!isbusy && isPlay && mSpMap.get(2) >= 0) {
            mSoundPool.play(mSpMap.get(2), 100, 100, 1, 0, 1f);
        }
    }

    /**
     * 声音提示
     */
    public void playDidiSound() {
        boolean isbusy = mEnableBusy;
        boolean isPlay = PreferenceUtil.getInstance().getBooleanValue(KEY_NOTIFY_SOUND, true);
        if (!isbusy && isPlay && mSpMap.get(1) >= 0) {
            mSoundPool.play(mSpMap.get(1), 100, 100, 1, 0, 1f);
        }
    }

    /**
     * 振动提示
     */
    public void playVibrate() {
        boolean isbusy = mEnableBusy;
        boolean isPlay = PreferenceUtil.getInstance().getBooleanValue(KEY_NOTIFY_VIBRATE, true);
        if (!isbusy && isPlay) {
            Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {10, 100, 20, 200}; // 停止 开启 停止 开启
            vibrator.vibrate(pattern, -1);
        }
    }

    private class InnerPhoneStateListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE: // 空闲
                    mEnableBusy = false;
                    break;
                case TelephonyManager.CALL_STATE_RINGING: // 来电
                case TelephonyManager.CALL_STATE_OFFHOOK: // 摘机（正在通话中）
                    mEnableBusy = true;
                    break;
            }
        }
    }
}
