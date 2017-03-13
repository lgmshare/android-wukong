package com.lgmshare.base.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.lgmshare.base.AppApplication;
import com.lgmshare.base.AppController;
import com.lgmshare.base.R;
import com.lgmshare.base.helper.ExecuteAsyncTaskHelper;
import com.lgmshare.base.helper.concurrency.Command;
import com.lgmshare.base.helper.concurrency.CommandCallback;
import com.lgmshare.base.ui.activity.base.BaseActivity;
import com.lgmshare.base.view.ActionBarLayout;
import com.lgmshare.component.utils.thread.ThreadPool;

/**
 * 应用设置
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/2/13 13:42
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected boolean isHideActionbar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initActionBar();
        initViews();
    }

    private void initActionBar() {
        ActionBarLayout layout = new ActionBarLayout(this);
        layout.setTitle("设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
        mActionBar.setCustomView(layout);
    }

    private void initViews() {
        findViewById(R.id.btn_clean_cache).setOnClickListener(this);
        findViewById(R.id.btn_guide).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clean_cache:
                clickCleanCache();
                break;
            case R.id.btn_guide:
                clickGuide();
                break;
        }
    }

    private void clickCleanCache() {
        ExecuteAsyncTaskHelper.execute(ATHIS, new Command<Integer>() {

            @Override
            public void start(CommandCallback<Integer> callback) {
                ThreadPool.runOnNonUITask(new Runnable() {
                    @Override
                    public void run() {
                        AppApplication.getInstance().cleanCache();
                    }
                });

                callback.onResult(0);
            }
        });
    }

    private void clickGuide() {
        AppController.startGuideActivity(ATHIS);
    }
}

