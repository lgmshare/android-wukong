package com.lgmshare.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgmshare.base.R;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/8/26 18:36
 * @email: lgmshare@gmail.com
 */
public class MainTabLayout extends FrameLayout {

    private ImageView tab_item_icon;
    private TextView tab_item_name;
    private TextView tab_item_num;

    private CharSequence mTitle;
    private int mTopImageNormal;
    private int mTopImageSelected;

    private boolean mSelected = false;

    public MainTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context);
    }

    public MainTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public MainTabLayout(Context context) {
        super(context);
        initViews(context);
    }

    private void initViews(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_tabbutton, this);
        tab_item_icon = (ImageView) view.findViewById(R.id.tab_item_icon);
        tab_item_name = (TextView) view.findViewById(R.id.tab_item_name);
        tab_item_num = (TextView) view.findViewById(R.id.tab_item_num);
        tab_item_num.setVisibility(GONE);
    }

    public void setIndicator(int title, int resNormal, int resSelected) {
        mTitle = getResources().getString(title);
        mTopImageNormal = resNormal;
        mTopImageSelected = resSelected;

        tab_item_name.setText(title);
        tab_item_icon.setImageResource(resNormal);
    }

    public void setIndicator(CharSequence title, int resNormal, int resSelected) {
        mTitle = title;
        mTopImageNormal = resNormal;
        mTopImageSelected = resSelected;

        tab_item_name.setText(title);
        tab_item_icon.setImageResource(resNormal);
    }

    public void selecte(boolean selected){
        if (mSelected && selected){
            return;
        }
        mSelected = selected;
        if (selected){
            tab_item_icon.setImageResource(mTopImageSelected);
        }else{
            tab_item_icon.setImageResource(mTopImageNormal);
        }
    }

    public void showMessageNum(int num){
        if (num > 0){
            tab_item_num.setText(String.valueOf(num));
            tab_item_num.setVisibility(VISIBLE);
        }else{
            tab_item_num.setVisibility(GONE);
        }
    }
}
