package com.lgmshare.base.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lgmshare.base.AppApplication;
import com.lgmshare.base.AppController;
import com.lgmshare.base.R;
import com.lgmshare.base.manager.AccountManager;
import com.lgmshare.base.model.User;
import com.lgmshare.base.ui.fragment.base.BaseReceiverFragment;
import com.lgmshare.base.widget.observable.ObservableScrollView;
import com.lgmshare.base.widget.observable.ObservableScrollViewCallbacks;
import com.lgmshare.base.widget.observable.ScrollState;
import com.lgmshare.base.widget.observable.ScrollUtils;
import com.lgmshare.component.image.ImageLoader;

import java.util.List;

/**
 * @author: lim
 * @description: TODO
 * @email: lgmshare@gmail.com
 * @datetime 2016/2/1
 */
public class PersonalFragment extends BaseReceiverFragment implements ObservableScrollViewCallbacks {


    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;
    private static final float MAX_TEXT_SCALE_DELTA_6 = 1f;

    private View mFlexibleImageView;
    private View mFlexibleOverlayView;
    private View mFlexibleActionbarBg;

    private ObservableScrollView mScrollView;
    private TextView mFlexibleTitleView;
    private int mFlexibleSpaceActionbarHeight;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private ImageView mFlexiblHeadimg;


    @Override
    protected int onObtainLayoutResId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initViews() {
        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
        mFlexibleSpaceActionbarHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_actionbar_height);

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        mFlexibleImageView = findViewById(R.id.flexible_image);
        mFlexibleOverlayView = findViewById(R.id.flexible_overlay);

        mFlexibleActionbarBg = findViewById(R.id.flexible_actionbar_bg);
        mFlexibleActionbarBg.setAlpha(0);

        mFlexiblHeadimg = (ImageView) findViewById(R.id.flexible_headimg);
        mFlexiblHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "FAB is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        mFlexibleTitleView = (TextView) findViewById(R.id.flexible_title);
        mFlexibleTitleView.setText("未登录");

        //初始效果
        ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(0, 0);
            }
        });
        findViewById(R.id.btn_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.startSettingActivity(getActivity());
            }
        });
        findViewById(R.id.btn_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.startAboutActivity(getActivity());
            }
        });
    }

    @Override
    protected void broadcastFilter(List<String> actions) {
        actions.add(AccountManager.ACITON_ACCOUNT_INFO_CHANGE_RECEVIER);
    }

    @Override
    protected void broadcastReceiver(String action) {
        if (action.equals(AccountManager.ACITON_ACCOUNT_INFO_CHANGE_RECEVIER)) {
            updateView();
        }
    }

    private void updateView() {
        if (AppApplication.getInstance().getAccountManager().isLogin()) {
            User user = AppApplication.getInstance().getAccountManager().getUser();
            if (user != null) {
                ImageLoader.getInstance().displayImage(user.getHeadimgurl(), mFlexiblHeadimg);
                mFlexibleTitleView.setText(user.getName());
            }
        } else {
            mFlexiblHeadimg.setImageResource(R.drawable.icon_phone);
            mFlexibleTitleView.setText("未登陆");
        }
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        float flexibleRange = mFlexibleSpaceImageHeight - mFlexibleSpaceActionbarHeight;
        int minOverlayTransitionY = mFlexibleSpaceActionbarHeight - mFlexibleOverlayView.getHeight();
        mFlexibleOverlayView.setTranslationY(ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        mFlexibleImageView.setTranslationY(ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Change alpha of overlay
        mFlexibleOverlayView.setAlpha(ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));
        mFlexibleActionbarBg.setAlpha(ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        mFlexibleTitleView.setPivotX(0);
        mFlexibleTitleView.setPivotY(0);
        mFlexibleTitleView.setScaleX(scale);
        mFlexibleTitleView.setScaleY(scale);

        // Translate title text
        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mFlexibleTitleView.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        titleTranslationY = titleTranslationY < 0 ? 0 : titleTranslationY;
        mFlexibleTitleView.setTranslationY(titleTranslationY);

        float scaleh = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA_6);
        mFlexiblHeadimg.setScaleX(scaleh);
        mFlexiblHeadimg.setScaleY(scaleh);

        // Translate title text
        int maxTitleTranslationhY = (int) (mFlexibleSpaceImageHeight - mFlexiblHeadimg.getHeight() * scale) - mFlexibleSpaceActionbarHeight * 2;
        int titleTranslationhY = maxTitleTranslationhY - scrollY;
        titleTranslationhY = titleTranslationhY < 0 ? 0 : titleTranslationhY;
        mFlexiblHeadimg.setTranslationY(titleTranslationhY);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

}
