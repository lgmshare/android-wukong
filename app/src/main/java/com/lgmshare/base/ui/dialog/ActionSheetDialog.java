package com.lgmshare.base.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lgmshare.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部单选项dialog
 *
 * @author: lim
 * @ClassName: ActionSheetDialog.java
 * @datetime 2014-11-11 上午9:38:40
 */
public class ActionSheetDialog extends Dialog {

    private TextView mTitleView;
    private TextView mCancelView;
    private LinearLayout mContentLayout;
    private ScrollView mContentScrollView;

    private CharSequence mTitle;
    private boolean mShowTitle;
    private List<SheetItem> mSheetItemList;
    private DisplayMetrics mMetrics = new DisplayMetrics();

    public ActionSheetDialog(Context context) {
        super(context, R.style.ActionSheetDialog);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(mMetrics);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_actionsheet);

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;

        mContentScrollView = (ScrollView) findViewById(R.id.scrollView);
        mContentLayout = (LinearLayout) findViewById(R.id.layout_content);
        mTitleView = (TextView) findViewById(R.id.txt_title);
        mCancelView = (TextView) findViewById(R.id.txt_cancel);
        mCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (!TextUtils.isEmpty(mTitle)) {
            mTitleView.setVisibility(View.VISIBLE);
            mTitleView.setText(mTitle);
        }
        setSheetItems();
    }

    @Override
    public void setTitle(int resId) {
        mTitle = getContext().getResources().getString(resId);
        if (mTitleView != null) {
            mTitleView.setText(resId);
            mTitleView.setVisibility(View.VISIBLE);
        }
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
        mShowTitle = true;
        if (mTitleView != null) {
            mTitleView.setVisibility(View.VISIBLE);
            mTitleView.setText(title);
        }
    }

    /**
     * @param item     条目名称
     * @param color    条目字体颜色，设置null则默认蓝色
     * @param listener
     * @return
     */
    public void addSheetItem(String item, SheetItemColor color, OnSheetItemClickListener listener) {
        if (mSheetItemList == null) {
            mSheetItemList = new ArrayList<SheetItem>();
        }
        mSheetItemList.add(new SheetItem(item, color, listener));
    }

    /**
     * @param items    条目名称
     * @param listener
     * @return
     */
    public void addStringArrayItem(String[] items, OnSheetItemClickListener listener) {
        if (mSheetItemList == null) {
            mSheetItemList = new ArrayList<SheetItem>();
        }
        int length = items.length;
        for (int i = 0; i < length; i++) {
            mSheetItemList.add(new SheetItem(items[i], SheetItemColor.Blue, listener));
        }
    }

    public void addStringArrayItem(String[] items, SheetItemColor[] color, OnSheetItemClickListener listener) {
        if (mSheetItemList == null) {
            mSheetItemList = new ArrayList<SheetItem>();
        }
        int length = items.length;
        for (int i = 0; i < length; i++) {
            mSheetItemList.add(new SheetItem(items[i], color[i], listener));
        }
    }

    /**
     * 设置条目布局
     */
    private void setSheetItems() {
        if (mSheetItemList == null || mSheetItemList.size() <= 0) {
            return;
        }

        int size = mSheetItemList.size();

        // TODO 高度控制，非最佳解决办法
        // 添加条目过多的时候控制高度
        if (size >= 6) {
            LayoutParams params = (LayoutParams) mContentScrollView.getLayoutParams();
            params.height = mMetrics.heightPixels / 2;
            mContentScrollView.setLayoutParams(params);
        }

        // 循环添加条目
        for (int i = 0; i < size; i++) {
            SheetItem sheetItem = mSheetItemList.get(i);
            String strItem = sheetItem.name;
            SheetItemColor color = sheetItem.color;
            final OnSheetItemClickListener listener = (OnSheetItemClickListener) sheetItem.itemClickListener;

            TextView textView = new TextView(getContext());
            textView.setTag(i);
            textView.setText(strItem);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);

            // 背景图片
            if (mShowTitle) {
                if (i >= 0 && i < size - 1) {
                    textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                } else {
                    textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                }
            } else {
                if (i == 0) {
                    textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                } else if (i < size - 1) {
                    textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                } else {
                    textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                }
            }

            // 字体颜色
            if (color == null) {
                textView.setTextColor(Color.parseColor(SheetItemColor.Blue.getName()));
            } else {
                textView.setTextColor(Color.parseColor(color.getName()));
            }

            // 高度
            float scale = getContext().getResources().getDisplayMetrics().density;
            int height = (int) (45 * scale + 0.5f);
            textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));

            // 点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (Integer) v.getTag();
                    listener.onClick(index);
                    dismiss();
                }
            });

            mContentLayout.addView(textView);
        }
    }

    public interface OnSheetItemClickListener {
        void onClick(int position);
    }

    public class SheetItem {
        String name;
        OnSheetItemClickListener itemClickListener;
        SheetItemColor color;

        public SheetItem(String name, SheetItemColor color, OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }

    public enum SheetItemColor {
        Blue("#037BFF"), Red("#FD4A2E");

        private String name;

        private SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
