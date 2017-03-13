package com.lgmshare.base.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lgmshare.base.AppController;
import com.lgmshare.base.R;
import com.lgmshare.base.http.HttpResponseHandler;
import com.lgmshare.base.http.task.AdvertisementTask;
import com.lgmshare.base.manager.AccountManager;
import com.lgmshare.base.model.Advertisement;
import com.lgmshare.base.ui.adapter.HomeAdvImageAdapter;
import com.lgmshare.base.ui.fragment.base.BaseReceiverFragment;
import com.lgmshare.base.view.ActionBarLayout;
import com.lgmshare.base.widget.CircleFlowIndicator;
import com.lgmshare.base.widget.PageLoadingLayout;
import com.lgmshare.base.widget.ViewFlow;
import com.lgmshare.component.logger.Logger;
import com.lgmshare.component.utils.DensityUtil;

import java.util.List;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/10/8 10:50
 * @email: lgmshare@gmail.com
 */
public class HomeFragment extends BaseReceiverFragment {

    private PageLoadingLayout mPageLoadingLayout;

    private ActionBarLayout mActionBarLayout;

    // 广告
    private ViewFlow mViewFlow;
    private CircleFlowIndicator mCircleFlowIndicator;
    private HomeAdvImageAdapter mHomeAdvImageAdapter;

    @Override
    protected int onObtainLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {
        mActionBarLayout = (ActionBarLayout) findViewById(R.id.actionbar);
        mActionBarLayout.setTitle("首页");
        mActionBarLayout.setRightTextButton(R.string.login, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.startUserLoginActivity(getActivity());
            }
        });

        // 加载等待layout
        mPageLoadingLayout = (PageLoadingLayout) findViewById(R.id.layout_loading);
        // 数据错误重载
        mPageLoadingLayout.setErrorButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        //根据屏幕2:5,设置banner宽高
        int screenWidth = DensityUtil.getScreenWidth(getActivity());
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout_adv);
        layout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, screenWidth * 2 / 5));
        mViewFlow = (ViewFlow) findViewById(R.id.viewflow);
        mCircleFlowIndicator = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        httpRequestAdvData();
    }

    private void httpRequestAdvData() {
        AdvertisementTask task = new AdvertisementTask();
        task.setHttpResponseHandler(new HttpResponseHandler<List<Advertisement>>() {

            @Override
            public void onStart() {
                super.onStart();
                mPageLoadingLayout.showLoading();
            }

            @Override
            public void onSuccess(List<Advertisement> advertisements) {
                showAdvertisementData(advertisements);
                mPageLoadingLayout.showNone();
            }

            @Override
            public void onFailure(int code, String error) {
                mPageLoadingLayout.setErrorMessage(error);
                mPageLoadingLayout.showError();
            }
        });
        task.send(getActivity());
    }

    /**
     * 轮播广告
     *
     * @param advList
     */
    private void showAdvertisementData(List<Advertisement> advList) {
        if (advList != null && advList.size() > 0) {
            // 点击在Adapter中
            mHomeAdvImageAdapter = new HomeAdvImageAdapter(getActivity());
            mHomeAdvImageAdapter.setList(advList);
            mViewFlow.setTimeSpan(4500);
            mViewFlow.setSelection(advList.size() * 1000);
            mViewFlow.setValidCount(advList.size());
            mViewFlow.setFlowIndicator(mCircleFlowIndicator);
            mViewFlow.setAdapter(mHomeAdvImageAdapter);
            mViewFlow.startAutoFlowTimer();

            mViewFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (mHomeAdvImageAdapter.getCount() == 0) {
                        return;
                    }
                    String url = mHomeAdvImageAdapter.getItem(i).getUrl();
                    if (TextUtils.isEmpty(url)) {
                        return;
                    }
                    try {
                        AppController.startWebViewActivity(getActivity(), R.string.app_name, url);
                    } catch (Exception e) {
                        Logger.e(TAG, url);
                    }
                }
            });
        }
    }

    @Override
    protected void broadcastFilter(List<String> actions) {
        actions.add(AccountManager.ACITON_ACCOUNT_INFO_CHANGE_RECEVIER);
    }

    @Override
    protected void broadcastReceiver(String action) {
        if (action.equals(AccountManager.ACITON_ACCOUNT_INFO_CHANGE_RECEVIER)) {
            mActionBarLayout.hideRightTextButton();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewFlow.startAutoFlowTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewFlow.stopAutoFlowTimer();
    }
}
