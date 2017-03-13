package com.lgmshare.base.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lgmshare.base.R;
import com.lgmshare.base.widget.wheel.OnWheelChangedListener;
import com.lgmshare.base.widget.wheel.WheelVerticalView;
import com.lgmshare.base.widget.wheel.adapter.AbstractWheelTextAdapter;
import com.lgmshare.base.widget.wheel.adapter.ArrayListWheelAdapter;

import java.util.List;

/**
 * 彷IOS单项选择控件
 * Created by lim.
 * Datetime: 2015/6/30.
 * Email: lgmshare@mgail.com
 */
public class WheelDialog extends Dialog {

    private static final int WHEEL_VISIBLE_ITEMS = 5;

    private TextView mTitleView;
    private Button mCancelButton;
    private Button mEnsureButton;
    private WheelVerticalView mWheelView;

    private CharSequence mTitle;
    private View.OnClickListener mEnsureClickListener;
    private View.OnClickListener mCancelClickListener;

    private int mWheelSheetIndex = 0;
    private boolean mWheelCyclic = false;
    private AbstractWheelTextAdapter mWheelTextAdapter;
    private OnWheelChangedListener mWheelChangedListener;

    private List<String> mDataList;

    public WheelDialog(Context context) {
        super(context, R.style.WheelVerticalViewDialog);
    }

    public WheelDialog(Context context, int selecteIndex) {
        super(context, R.style.WheelVerticalViewDialog);
        mWheelSheetIndex = selecteIndex;
    }

    public WheelDialog(Context context, int selecteIndex, boolean cyclic) {
        super(context, R.style.WheelVerticalViewDialog);
        mWheelSheetIndex = selecteIndex;
        mWheelCyclic = cyclic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_wheel_sheet);

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;

        mTitleView = (TextView) findViewById(R.id.tv_title);
        mWheelView = (WheelVerticalView) findViewById(R.id.wheelview);

        mEnsureButton = (Button) findViewById(R.id.btn_ensure);
        mEnsureButton.setVisibility(View.VISIBLE);
        mEnsureButton.setOnClickListener(mEnsureClickListener);

        mCancelButton = (Button) findViewById(R.id.btn_cancel);
        mCancelButton.setVisibility(View.VISIBLE);
        mCancelButton.setOnClickListener(mCancelClickListener);

        mWheelView.setViewAdapter(mWheelTextAdapter);// 设置显示数据
        mWheelView.setCyclic(mWheelCyclic);// 可循环滚动
        mWheelView.setVisibleItems(WHEEL_VISIBLE_ITEMS);
        mWheelView.setCurrentItem(mWheelSheetIndex);// 初始化时显示的数据

        if (!TextUtils.isEmpty(mTitle)) {
            mTitleView.setVisibility(View.VISIBLE);
            mTitleView.setText(mTitle);
        }

        if (mEnsureClickListener == null) {
            mEnsureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        if (mCancelClickListener == null) {
            mCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    @Override
    public void setTitle(int resId) {
        mTitle = getContext().getResources().getString(resId);
        if (mTitleView != null) {
            mTitleView.setText(resId);
            mTitleView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 添加标题
     *
     * @param title 标题
     */
    public void setTitle(CharSequence title) {
        mTitle = title;
        if (mTitleView != null) {
            mTitleView.setVisibility(View.VISIBLE);
            mTitleView.setText(title);
        }
    }

    public void setOnCancelClickListener(View.OnClickListener listener) {
        mCancelClickListener = listener;
        if (mEnsureButton != null) {
            mCancelButton.setVisibility(View.VISIBLE);
            mCancelButton.setOnClickListener(listener);
        }
    }

    public void setOnEnsureClickListener(View.OnClickListener listener) {
        mEnsureClickListener = listener;
        if (mEnsureButton != null) {
            mEnsureButton.setVisibility(View.VISIBLE);
            mEnsureButton.setOnClickListener(listener);
        }
    }

    /**
     * 滚动选择项改变监听事件
     *
     * @param listener
     */
    public void setOnWheelChangedListener(OnWheelChangedListener listener) {
        mWheelChangedListener = listener;
        if (mWheelView != null) {
            mWheelView.addChangingListener(listener);
        }
    }

    public void setWheelSelecteIndex(int selecteIndex) {
        this.mWheelSheetIndex = selecteIndex;
        if (mWheelView != null) {
            mWheelView.setCurrentItem(selecteIndex);
        }
    }

    public void setWheelCyclic(boolean cyclic) {
        this.mWheelCyclic = cyclic;
        if (mWheelView != null) {
            mWheelView.setCyclic(cyclic);// 可循环滚动
        }
    }

    private void setWheelTextAdapter(AbstractWheelTextAdapter adapter) {
        this.mWheelTextAdapter = adapter;
        if (mWheelView != null) {
            mWheelView.setViewAdapter(adapter);
        }
    }

    public void setDefaultStringArrayAdapter(List<String> list, int resLayout, int resId) {
        mDataList = list;
        ArrayListWheelAdapter adapter = new ArrayListWheelAdapter<String>(getContext(), list);
        adapter.setTextColor(getContext().getResources().getColor(R.color.gray_4c4c4c));
        adapter.setTextSize(18);
        adapter.setItemResource(resLayout);
        adapter.setItemTextResource(resId);
        setWheelTextAdapter(adapter);
    }

    public int getWheelSheetIndex() {
        return mWheelView.getCurrentItem();
    }

    public String getWheelSheetValue() {
        return mDataList.get(mWheelView.getCurrentItem());
    }
}
