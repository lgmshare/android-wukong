package com.lgmshare.base.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.lgmshare.base.R;
import com.lgmshare.base.ui.fragment.base.BaseFragment;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/9/11 18:15
 * @email: lgmshare@gmail.com
 */
public class GuideImageFragment extends BaseFragment {

    private static String EXTRA_POSITION = "position";
    private static String EXTRA_RESOURCE_ID = "resId";
    private static String EXTRA_SHOW_CLOSE = "showClose";

    public static Fragment getInstance(int position, int resId, boolean showClose) {
        Bundle bd = new Bundle();
        bd.putInt(EXTRA_POSITION, position);
        bd.putInt(EXTRA_RESOURCE_ID, resId);
        bd.putBoolean(EXTRA_SHOW_CLOSE, showClose);
        Fragment fragment = new GuideImageFragment();
        fragment.setArguments(bd);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int onObtainLayoutResId() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void initViews() {
        FrameLayout layout = (FrameLayout) findViewById(R.id.ll_bg);
        layout.setBackgroundResource(getArguments().getInt(EXTRA_RESOURCE_ID));

        Button button = (Button) findViewById(R.id.btn_check);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnFragmentMessageListener != null) {
                    mOnFragmentMessageListener.onFragmentSendMessage(0, "close");
                }
            }
        });
        if (getArguments().getBoolean(EXTRA_SHOW_CLOSE)) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
    }
}
