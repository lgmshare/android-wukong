package com.lgmshare.base.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lgmshare.base.AppContext;
import com.lgmshare.base.R;
import com.lgmshare.base.ui.activity.base.BaseActivity;
import com.lgmshare.base.ui.dialog.CommonDialog;
import com.lgmshare.base.view.ActionBarLayout;

/**
 * 关于我们
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/2/13 13:49
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {

    @Override
    protected boolean isHideActionbar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initActionBar();
        initViews();
    }

    private void initActionBar() {
        ActionBarLayout layout = new ActionBarLayout(this);
        layout.setTitle("关于我们", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.this.finish();
            }
        });
        mActionBar.setCustomView(layout);
    }

    private void initViews() {
        TextView tv_clientVersion = (TextView) findViewById(R.id.tv_clientVersion);
        TextView tv_clientVersionBuild = (TextView) findViewById(R.id.tv_clientVersionBuild);
        TextView tv_company = (TextView) findViewById(R.id.tv_company);

        tv_clientVersion.setText(AppContext.getVersionName());
        tv_clientVersion.setOnLongClickListener(this);
        tv_clientVersionBuild.setText("code:" + AppContext.getVersionCode());
        tv_company.setText("....................");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clientVersion:
                clickShowInformation();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clientVersion:
                clickShowInformation();
                break;
            default:
                break;
        }
        return true;
    }

    private void clickShowInformation() {
        StringBuilder sb = new StringBuilder();
        sb.append("VersionName:" + AppContext.getVersionName() + "\n");
        sb.append("VersionCode:" + AppContext.getVersionCode() + "\n");
        sb.append("DebugMode:" + AppContext.isDebugModel() + "\n");
        sb.append("SDK Version:" + Build.VERSION.SDK_INT);
        final CommonDialog dialog = CommonDialog.create(ATHIS, CommonDialog.MODE_NONE_CANCEL);
        dialog.setContent(sb.toString());
        dialog.setOnPositiveClick("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
