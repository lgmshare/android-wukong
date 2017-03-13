package com.lgmshare.base.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lgmshare.base.R;
import com.lgmshare.base.ui.fragment.base.BaseFragment;
import com.lgmshare.component.logger.Logger;

/**
 * @author: lim
 * @description: TODO
 * @email: lgmshare@gmail.com
 * @datetime 2016/1/18
 */
public class Tab2ContainerFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab2, null);
    }

    @Override
    protected int onObtainLayoutResId() {
        return R.layout.fragment_tab2;
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.tab_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) getView().findViewById(R.id.tab_text);
                textView.setText("ddddddddddddddddddddddddddddddddddd");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("Tab2ContainerFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d("Tab2ContainerFragment onPause");
    }
}