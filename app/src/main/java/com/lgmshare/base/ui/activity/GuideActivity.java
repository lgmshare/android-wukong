package com.lgmshare.base.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.lgmshare.base.R;
import com.lgmshare.base.ui.activity.base.BaseFragmentActivity;
import com.lgmshare.base.ui.fragment.GuideImageFragment;
import com.lgmshare.base.ui.fragment.base.BaseFragment;

/**
 * 应用引导页
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/9/11 10:45
 * @email: lgmshare@gmail.com
 */
public class GuideActivity extends BaseFragmentActivity implements BaseFragment.OnFragmentMessageListener {

    private PagerAdapter mViewPagerAdapter;

    @Override
    protected boolean isHideActionbar() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initViews();
        overridePendingTransition(R.anim.fade_in, R.anim.none);
    }

    private void initViews() {
        mViewPagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.none, R.anim.fade_out);
    }

    @Override
    public void onFragmentSendMessage(int position, String message) {
        if ("close".equals(message)) {
            clickClose();
        }
    }

    @Override
    public void onFragmentChange(int oldPosition, int newPosition) {

    }

    private void clickClose() {
        GuideActivity.this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        Fragment[] fragments = new Fragment[5];

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (fragments[position] == null) {
                switch (position) {
                    case 0:
                        fragments[position] = GuideImageFragment.getInstance(position, R.drawable.bg_guide_1, false);
                        break;
                    case 1:
                        fragments[position] = GuideImageFragment.getInstance(position, R.drawable.bg_guide_2, false);
                        break;
                    case 2:
                        fragments[position] = GuideImageFragment.getInstance(position, R.drawable.bg_guide_3, false);
                        break;
                    case 3:
                        fragments[position] = GuideImageFragment.getInstance(position, R.drawable.bg_guide_4, false);
                        break;
                    case 4:
                        fragments[position] = GuideImageFragment.getInstance(position, R.drawable.bg_guide_5, true);
                        break;
                }
            }
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}
