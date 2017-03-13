package com.lgmshare.base.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lgmshare.base.AppController;
import com.lgmshare.base.R;
import com.lgmshare.base.ui.activity.base.BaseActivity;
import com.lgmshare.base.view.ActionBarLayout;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/9/10 17:46
 * @email: lgmshare@gmail.com
 */
public class UserRegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etPasswordArgin;
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        initActionBar();
        initViews();
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.none);
    }

    private void initActionBar(){
        ActionBarLayout layout = new ActionBarLayout(ATHIS);
        layout.setTitle("注册", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegisterActivity.this.finish();
            }
        });
        mActionBar.setCustomView(layout);
    }

    private void initViews(){
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPasswordArgin = (EditText) findViewById(R.id.et_password_argin);
        etPhone = (EditText) findViewById(R.id.et_phone);
        findViewById(R.id.btn_rigester).setOnClickListener(this);
    }

    @Override
    protected boolean isHideActionbar() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_rigester:
                AppController.backHomePage();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.none, R.anim.slide_out_bottom);
    }
}

