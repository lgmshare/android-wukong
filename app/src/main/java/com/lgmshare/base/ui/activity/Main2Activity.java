package com.lgmshare.base.ui.activity;

import android.os.Bundle;

import com.lgmshare.base.ui.activity.base.BaseFragmentActivity;
import com.lgmshare.base.ui.fragment.Tab1ContainerFragment;
import com.lgmshare.base.ui.fragment.Tab2ContainerFragment;
import com.lgmshare.component.widget.FragmentTabHost;
import com.lgmshare.base.R;


/**
 * @author: lim
 * @description: TODO
 * @email: lgmshare@gmail.com
 * @datetime 2016/1/5
 */
public class Main2Activity extends BaseFragmentActivity {

    private static final String TAB_1_TAG = "tab_1";
    private static final String TAB_2_TAG = "tab_2";

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected boolean isHideActionbar() {
        return false;
    }

    private void initView() {
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_1_TAG).setIndicator("tab1"), Tab1ContainerFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_2_TAG).setIndicator("tab2"), Tab2ContainerFragment.class, null);
    }
}
