package cn.sharesdk.login;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

public class OnekeyLogin implements Callback {
    private static final int MSG_AUTH_CANCEL = 1;
    private static final int MSG_AUTH_ERROR = 2;
    private static final int MSG_AUTH_COMPLETE = 3;

    private OnLoginListener mOnLoginListener;
    private String platform;
    private Context context;
    private Handler handler;

    public OnekeyLogin() {
        handler = new Handler(Looper.getMainLooper(), this);
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setOnLoginListener(OnLoginListener login) {
        this.mOnLoginListener = login;
    }

    public void login(Context context) {
        this.context = context.getApplicationContext();
        if (platform == null) {
            return;
        }

        //初始化SDK
        ShareSDK.initSDK(context);
        Platform plat = ShareSDK.getPlatform(platform);
        if (plat == null) {
            return;
        }

        if (plat.isAuthValid()) {
            plat.removeAccount(true);
            return;
        }

        //使用SSO授权，通过客户单授权
        plat.SSOSetting(false);
        plat.setPlatformActionListener(new PlatformActionListener() {
            public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
                if (action == Platform.ACTION_USER_INFOR) {
                    Message msg = new Message();
                    msg.what = MSG_AUTH_COMPLETE;
                    msg.arg2 = action;
                    msg.obj = new Object[]{plat.getName(), res};
                    handler.sendMessage(msg);
                }
            }

            public void onError(Platform plat, int action, Throwable t) {
                if (action == Platform.ACTION_USER_INFOR) {
                    Message msg = new Message();
                    msg.what = MSG_AUTH_ERROR;
                    msg.arg2 = action;
                    msg.obj = t;
                    handler.sendMessage(msg);
                }
                t.printStackTrace();
            }

            public void onCancel(Platform plat, int action) {
                if (action == Platform.ACTION_USER_INFOR) {
                    Message msg = new Message();
                    msg.what = MSG_AUTH_CANCEL;
                    msg.arg2 = action;
                    msg.obj = plat;
                    handler.sendMessage(msg);
                }
            }
        });
        plat.showUser(null);
    }

    /**
     * 处理操作结果
     */
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_AUTH_CANCEL: {
                // 取消
                if (mOnLoginListener != null) {
                    mOnLoginListener.onCancel();
                }
            }
            break;
            case MSG_AUTH_ERROR: {
                // 失败
                Throwable t = (Throwable) msg.obj;
                t.printStackTrace();
                if (mOnLoginListener != null) {
                    mOnLoginListener.onFailure(t.getMessage());
                }
            }
            break;
            case MSG_AUTH_COMPLETE: {
                // 成功
                Object[] objs = (Object[]) msg.obj;
                if (mOnLoginListener != null) {
                    String plat = (String) objs[0];
                    HashMap<String, Object> res = (HashMap<String, Object>) objs[1];

                    ShareUser shareUser = new ShareUser();
                    shareUser.setAdditional(res);
                    Platform pf = ShareSDK.getPlatform(plat);
                    if (pf != null) {
                        String gender = pf.getDb().getUserGender();
                        if ("m".equals(gender)) {
                            shareUser.setUserGender(ShareUser.Gender.MALE);
                        } else {
                            shareUser.setUserGender(ShareUser.Gender.FEMALE);
                        }
                        shareUser.setUserIcon(pf.getDb().getUserIcon());
                        shareUser.setUserName(pf.getDb().getUserName());
                        shareUser.setUserId(pf.getDb().getUserId());
                    }
                    mOnLoginListener.onSuccess(plat, shareUser);
                }
            }
            break;
        }
        return false;
    }
}
