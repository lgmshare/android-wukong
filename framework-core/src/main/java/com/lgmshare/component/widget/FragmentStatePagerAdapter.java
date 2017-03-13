package com.lgmshare.component.widget;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FragmentStatePagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    private FragmentManager mFragmentManager;
    /**
     * 每个Fragment对应一个Page
     */
    private List<Fragment> mFragmentList;
    /**
     * title 对应于每个大Fragment的小Fragment
     */
    private String[] mFragmentPageTitles;
    /**
     * 当前page索引（切换之前）
     */
    private int mCurrentPageIndex = 0;
    /**
     * ViewPager切换页面时的额外功能添加接口
     */
    private OnExtraPageChangeListener mOnExtraPageChangeListener;

    public FragmentStatePagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments, String[] titles) {
        this.mFragmentManager = fragmentManager;
        this.mFragmentList = fragments;
        this.mFragmentPageTitles = titles;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mFragmentList.get(position).getView()); // 移出viewpager两边之外的page布局
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = mFragmentList.get(position);
        if (!fragment.isAdded()) { // 如果fragment还没有added
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
            ft.commit();
            /**
             * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
             * 会在进程的主线程中，用异步的方式来执行。
             * 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
             * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
             */
            mFragmentManager.executePendingTransactions();
        }

        if (fragment.getView().getParent() == null) {
            container.addView(fragment.getView()); // 为viewpager增加布局
        }

        return fragment.getView();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (null != mOnExtraPageChangeListener) { // 如果设置了额外功能接口
            mOnExtraPageChangeListener.onExtraPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mFragmentList.get(mCurrentPageIndex).onPause(); // 调用切换前Fargment的onPause()
        if (mFragmentList.get(position).isAdded()) {
            mFragmentList.get(position).onResume(); // 调用切换后Fargment的onResume()
        }
        mCurrentPageIndex = position;

        if (null != mOnExtraPageChangeListener) { // 如果设置了额外功能接口
            mOnExtraPageChangeListener.onExtraPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int position) {
        if (null != mOnExtraPageChangeListener) { // 如果设置了额外功能接口
            mOnExtraPageChangeListener.onExtraPageScrollStateChanged(position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentPageTitles[position % mFragmentPageTitles.length];
    }

    /**
     * 当前page索引（切换之前）
     *
     * @return
     */
    public int getCurrentPageIndex() {
        return mCurrentPageIndex;
    }

    /**
     * 设置页面切换额外功能监听器
     *
     * @param onExtraPageChangeListener
     */
    public void setOnExtraPageChangeListener(OnExtraPageChangeListener onExtraPageChangeListener) {
        this.mOnExtraPageChangeListener = onExtraPageChangeListener;
    }

    /**
     * page切换额外功能接口
     */

    public static class OnExtraPageChangeListener {
        public void onExtraPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onExtraPageSelected(int position) {
        }

        public void onExtraPageScrollStateChanged(int position) {
        }
    }

}
