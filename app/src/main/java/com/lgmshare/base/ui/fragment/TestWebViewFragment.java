package com.lgmshare.base.ui.fragment;

import android.view.View;

import com.lgmshare.base.AppController;
import com.lgmshare.base.R;
import com.lgmshare.base.ui.fragment.base.BaseFragment;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/11/10 16:38
 * @email: lgmshare@gmail.com
 */
public class TestWebViewFragment extends BaseFragment {

    @Override
    protected int onObtainLayoutResId() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void initViews() {
        //点击调用js中方法
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               AppController.startWebViewActivity(getActivity(), R.string.app_name, "http://www.baidu.com");
            }
        });
    }
}
