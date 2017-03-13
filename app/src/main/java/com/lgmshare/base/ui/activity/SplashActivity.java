package com.lgmshare.base.ui.activity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;

import com.lgmshare.base.AppApplication;
import com.lgmshare.base.AppConfig;
import com.lgmshare.base.AppController;
import com.lgmshare.base.AppSettings;
import com.lgmshare.base.R;
import com.lgmshare.base.helper.ExecuteAsyncTaskHelper;
import com.lgmshare.base.helper.concurrency.Command;
import com.lgmshare.base.helper.concurrency.CommandCallback;
import com.lgmshare.base.ui.activity.base.BaseActivity;
import com.lgmshare.base.ui.dialog.CommonDialog;
import com.lgmshare.base.ui.dialog.EnvironmentDialog;
import com.lgmshare.component.logger.Logger;
import com.lgmshare.component.utils.thread.ThreadHandlerUtils;
import com.lgmshare.component.utils.thread.ThreadPool;

/**
 * splash页
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/2/13 13:49
 */
public class SplashActivity extends BaseActivity {

    private final int SPLASH_TIME_DELAY = AppConfig.SPLASH_TIME_DELAY; // 延迟

    @Override
    protected boolean isHideActionbar() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        displaySplash();
    }

    private void displaySplash() {
        //TODO 初始化系统配置及数据
        if (AppSettings.getInstance().isInitialized()) {
            //TODO 开启一个倒计时
            new CountDownTimer(SPLASH_TIME_DELAY, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Logger.d(TAG, "millisUntilFinished:" + millisUntilFinished + "");
                }

                @Override
                public void onFinish() {
                    displayNextPage();
                }
            }.start();
        } else {
            ExecuteAsyncTaskHelper.execute(this, null, new Command<Integer>() {
                @Override
                public void start(final CommandCallback<Integer> callback) {
                    //开启一个线程执行
                    ThreadPool.runOnNonUITask(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);
                                callback.onResult(0);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                callback.onError(e);
                            }

                        }
                    });
                }
            }, new CommandCallback<Integer>() {
                @Override
                public void onResult(Integer result) {
                    AppSettings.getInstance().initialized(true);
                    ThreadHandlerUtils.runOnUITask(new Runnable() {
                        @Override
                        public void run() {
                            displayNextPage();
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    ThreadHandlerUtils.runOnUITask(new Runnable() {
                        @Override
                        public void run() {
                            final CommonDialog dialog = CommonDialog.create(ATHIS, CommonDialog.MODE_NONE_CANCEL);
                            dialog.setTitle("运行异常");
                            dialog.setContent("数据初始化错误,应用即将退出");
                            dialog.setOnPositiveClick(R.string.ensure, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    AppApplication.getInstance().exit();
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    private void displayNextPage() {
        if (AppConfig.DEBUG_RUNTIME) {
            displayRuntimeDialog();
        } else {
            if (AppSettings.getInstance().isNewVersion()) {
                AppController.startGuideAndMainActivity(SplashActivity.this);
            } else {
                AppController.startMainActivity(SplashActivity.this);
            }
        }
    }

    /**
     * 选择运行环境
     */
    private void displayRuntimeDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag(EnvironmentDialog.TAG);
        if (null != fragment) {
            ft.remove(fragment);
        }
        DialogFragment dialog = new EnvironmentDialog();
        dialog.show(ft, EnvironmentDialog.TAG);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}