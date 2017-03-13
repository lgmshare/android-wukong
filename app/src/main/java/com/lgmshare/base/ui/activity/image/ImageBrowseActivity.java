package com.lgmshare.base.ui.activity.image;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lgmshare.base.R;
import com.lgmshare.base.ui.activity.base.BaseActivity;
import com.lgmshare.base.widget.HackyViewPager;
import com.lgmshare.component.image.ImageLoader;
import com.lgmshare.component.image.ImageLoaderOption;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片浏览页面（类似微信的图片浏览查看）
 *
 * @ClassName: ImageBrowseActivity.java
 * @datetime 2014-11-27 下午5:57:45
 */
public class ImageBrowseActivity extends BaseActivity {

    public static final String EXTRA_IMAGE_URLS = "image_urls";
    public static final String EXTRA_IMAGE_SELECTED_INDEX = "selected_index";

    private ViewPager mViewPager;
    private ImageBrowsePagerAdapter mImageBrowsePagerAdapter;
    private LinearLayout mIndicatorViewGroup;

    private List<String> mImageUrlList;
    private int mLastIndicator = 0;
    private int mCurIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browse);

        getIntentExtras();
        initViews();
    }

    @Override
    protected boolean isHideActionbar() {
        return false;
    }

    /**
     * 获得Intent传递的Extras参数
     */
    private void getIntentExtras() {
        mImageUrlList = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
        mCurIndex = getIntent().getExtras().getInt(EXTRA_IMAGE_SELECTED_INDEX);
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        mImageBrowsePagerAdapter = new ImageBrowsePagerAdapter(this, mImageUrlList);
        mImageBrowsePagerAdapter.setCurrentActivity(this);
        mViewPager.setAdapter(mImageBrowsePagerAdapter);
        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                mIndicatorViewGroup.getChildAt(position).setEnabled(true);
                mIndicatorViewGroup.getChildAt(mLastIndicator).setEnabled(false);
                mLastIndicator = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        initIndicator(mImageUrlList.size());
        mViewPager.setCurrentItem(mCurIndex);
    }

    /**
     * 初始化小圆点指示区域
     *
     * @param pageTotalCount 小圆点个数
     */
    private void initIndicator(int pageTotalCount) {
        mIndicatorViewGroup = (LinearLayout) findViewById(R.id.layout_indicator);
        if (pageTotalCount > 0) {
            int imgViewPadding = 5;
            for (int i = 0; i < pageTotalCount; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setPadding(imgViewPadding, imgViewPadding, imgViewPadding, imgViewPadding);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(layoutParams);
                imageView.setImageResource(R.drawable.bg_pager_dot_selector);
                imageView.setEnabled(false);
                mIndicatorViewGroup.addView(imageView);
            }
            mIndicatorViewGroup.getChildAt(mCurIndex).setEnabled(true);// 默认第一个为选中状态
        }

        if (mIndicatorViewGroup.getChildCount() > 0) {
            mIndicatorViewGroup.setVisibility(View.VISIBLE);
        }
    }

    class ImageBrowsePagerAdapter extends PagerAdapter {

        private LayoutInflater mLayoutInflater;
        private List<String> mImageUrlList;

        private Activity mCurrentActivity;

        public ImageBrowsePagerAdapter(Context context, List<String> imageUrlList) {
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mImageUrlList = imageUrlList;
        }

        public void setCurrentActivity(Activity currentActivity) {
            mCurrentActivity = currentActivity;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            View view = mLayoutInflater.inflate(R.layout.adapter_image_browse_item, container, false);
            PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View arg0, float arg1, float arg2) {
                    if (null != mCurrentActivity) {
                        mCurrentActivity.finish();
                    }
                }
            });
            String imageUrl = mImageUrlList.get(position);
            ImageLoader.getInstance().displayImage(imageUrl, photoView, ImageLoaderOption.getDisplayImageOptions(R.drawable.ic_launcher));
            container.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            return view;
        }

        @Override
        public int getCount() {
            return mImageUrlList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
