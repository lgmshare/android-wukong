package com.lgmshare.base.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TabHost;

import com.lgmshare.base.AppApplication;
import com.lgmshare.base.R;
import com.lgmshare.base.ui.activity.base.BaseFragmentActivity;
import com.lgmshare.base.ui.dialog.CommonDialog;
import com.lgmshare.base.ui.fragment.HomeFragment;
import com.lgmshare.base.ui.fragment.NearbyFragment;
import com.lgmshare.base.ui.fragment.PersonalFragment;
import com.lgmshare.base.ui.fragment.TestWebViewFragment;
import com.lgmshare.base.ui.fragment.WeekEventFragment;
import com.lgmshare.base.ui.fragment.base.BaseFragment;
import com.lgmshare.base.view.MainTabLayout;
import com.lgmshare.component.widget.FragmentTabHost;

/**
 * 应用首页
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/2/13 13:49
 */
public class MainActivity extends BaseFragmentActivity implements BaseFragment.OnFragmentMessageListener {

    //定义FragmentTabHost对象
    private FragmentTabHost mTabHost;
    private int mTabIndex = 0;

    private long mLastPressBackTime;

    @Override
    protected boolean isHideActionbar() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        //实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                MainTabLayout lastView = (MainTabLayout) mTabHost.getTabWidget().getChildAt(mTabIndex);
                lastView.selecte(false);

                mTabIndex = Integer.parseInt(tabId);
                MainTabLayout currView = (MainTabLayout) mTabHost.getTabWidget().getChildAt(mTabIndex);
                currView.selecte(true);
            }
        });
        int count = MainTabFactory.TAB_COUNT;
        for (int i = 0; i < count; i++) {
            addTabItem(i);
        }
    }

    private void addTabItem(int index) {
        //Tab按钮设置图标、文字和内容
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec(String.valueOf(index));
        tabSpec.setIndicator(MainTabFactory.getTabItemView(ATHIS, index));
        //将Tab按钮添加进Tab选项卡中
        mTabHost.addTab(tabSpec, MainTabFactory.getFragmentClass(index), null);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 显示退出提示对话框
     */
    private void showExitAlertDialog() {
        final CommonDialog mCommonAlertDialog = CommonDialog.create(this);
        mCommonAlertDialog.setTitle(getString(R.string.txt_quit_app));
        mCommonAlertDialog.setContent(getString(R.string.txt_are_you_sure_to_quit));
        mCommonAlertDialog.setOnNegativeClick(R.string.ensure,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCommonAlertDialog.dismiss();
                        AppApplication.getInstance().exit();
                    }
                });
        mCommonAlertDialog.setOnPositiveClick(R.string.cancel,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCommonAlertDialog.dismiss();
                    }
                });
        mCommonAlertDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long time = mLastPressBackTime == 0 ? Long.MAX_VALUE : mLastPressBackTime;
            if (Math.abs(time - System.currentTimeMillis()) < 3 * 1000) {
                AppApplication.getInstance().exit();
            } else {
                mLastPressBackTime = System.currentTimeMillis();
                showToastMessage("亲，再按一次退出系统!");
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFragmentSendMessage(int position, String message) {
        MainTabLayout tabLayout = (MainTabLayout) mTabHost.getTabWidget().getChildAt(position);
        tabLayout.showMessageNum(2);
    }

    @Override
    public void onFragmentChange(int oldPosition, int newPosition) {
        mTabHost.onTabChanged(String.valueOf(newPosition));
    }

    static class MainTabFactory {
        protected static final int TAB_COUNT = 5;
        protected static final int TAB_0 = 0;
        protected static final int TAB_1 = 1;
        protected static final int TAB_2 = 2;
        protected static final int TAB_3 = 3;
        protected static final int TAB_4 = 4;

        public static View getTabItemView(Context context, int index) {
            MainTabLayout tabView = new MainTabLayout(context);
            switch (index) {
                case TAB_0:
                    tabView.setIndicator("首页", R.drawable.tab_home, R.drawable.tab_home_selected);
                    break;
                case TAB_1:
                    tabView.setIndicator("排期", R.drawable.tab_company, R.drawable.tab_company_selected);
                    break;
                case TAB_2:
                    tabView.setIndicator("好友", R.drawable.tab_account, R.drawable.tab_account_selected);
                    break;
                case TAB_3:
                    tabView.setIndicator("广场", R.drawable.tab_account, R.drawable.tab_account_selected);
                    break;
                case TAB_4:
                    tabView.setIndicator("更多", R.drawable.tab_account, R.drawable.tab_account_selected);
                    break;
            }
            //设置Tab按钮的背景
            tabView.setBackgroundResource(R.drawable.bg_transparent_blue_selector);
            return tabView;
        }

        public static Class getFragmentClass(int index) {
            Class clazz = null;
            switch (index) {
                case TAB_0:
                    clazz = HomeFragment.class;
                    break;
                case TAB_1:
                    clazz = NearbyFragment.class;
                    break;
                case TAB_2:
                    clazz = WeekEventFragment.class;
                    break;
                case TAB_3:
                    clazz = TestWebViewFragment.class;
                    break;
                case TAB_4:
                    clazz = PersonalFragment.class;
                    break;
                default:
                    clazz = HomeFragment.class;
                    break;
            }
            return clazz;
        }
    }
}
