package com.lgmshare.base.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lgmshare.base.R;
import com.lgmshare.base.ui.fragment.base.BaseFragment;
import com.lgmshare.component.logger.Logger;

/**
 * @author: lim
 * @description: TODO
 * @email: lgmshare@gmail.com
 * @datetime 2016/1/18
 */
public class Tab1ContainerFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_test, null);
    }

    @Override
    protected int onObtainLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("Tab1ContainerFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d("Tab1ContainerFragment onPause");
    }
}
