package com.lgmshare.base.ui.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lgmshare.base.AppApplication;
import com.lgmshare.base.AppContext;
import com.lgmshare.base.AppController;
import com.lgmshare.base.R;
import com.lgmshare.base.http.HttpResponseHandler;
import com.lgmshare.base.http.task.UserLoginTask;
import com.lgmshare.base.manager.AccountManager;
import com.lgmshare.base.model.User;
import com.lgmshare.base.ui.activity.base.BaseActivity;
import com.lgmshare.base.view.ActionBarLayout;

/**
 * TODO
 * Created by Administrator.
 * Datetime: 2015/6/30.
 * Email: lgmshare@mgail.com
 */
public class UserLoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.none);
        setContentView(R.layout.activity_user_login);
        initActionBar();
        initViews();
    }

    @Override
    protected boolean isHideActionbar() {
        return false;
    }

    private void initActionBar() {
        ActionBarLayout layout = new ActionBarLayout(this);
        layout.setTitle(R.string.back_home);
        layout.setLeftTextButton(R.drawable.back, "返回", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mActionBar.setCustomView(layout);
    }

    private void initViews() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        AccountManager accountManager = AppApplication.getInstance().getAccountManager();
        String username = accountManager.getLastLoginUsername();
        String password = accountManager.getLastLoginPassword();
        if (!TextUtils.isEmpty(username)) {
            etUsername.setText(username);
            etUsername.setSelection(username.length());
            if (!TextUtils.isEmpty(password)) {
                etPassword.setText(password);
                etPassword.setSelection(password.length());
            }
            //默认不弹出软键盘
            hideSoftInput();
        } else if (AppContext.isDebugModel()) {
            etUsername.setText("15208155754");
            etPassword.setText("123456");
            etUsername.setSelection("15208155754".length());
            //默认不弹出软键盘
            hideSoftInput();
        }
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_forget).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //TODO implement
                httpRequestLogin();
                break;
            case R.id.btn_forget:
                //TODO implement
                AppController.startUserRegisterActivity(UserLoginActivity.this);
                break;
        }
    }

    private void httpRequestLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            showToastMessage("请输入用户名");
        } else if (TextUtils.isEmpty(password)) {
            showToastMessage("请输入密码");
        } else if (password.length() < 6) {
            showToastMessage("密码不能小于6位");
        } else {
            hideSoftInput();
            UserLoginTask task = new UserLoginTask(username, password, AppContext.getAppUniqueID());
            task.setHttpResponseHandler(new HttpResponseHandler<User>() {

                @Override
                public void onStart() {
                    showProgressDialog("");
                }

                @Override
                public void onSuccess(User user) {
                    loginSuccess(user);
                }

                @Override
                public void onFailure(int code, String error) {
                    showToastMessage(error);
                }

                @Override
                public void onFinish() {
                    dismissProgressDialog();
                }
            });
            task.send(this);
        }
    }

    private void loginSuccess(User user) {
        AccountManager accountManager = AppApplication.getInstance().getAccountManager();
        accountManager.setUser(user);
        AppController.back(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.none, R.anim.slide_out_bottom);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
        }
    }
}
