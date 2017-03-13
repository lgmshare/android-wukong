package com.lgmshare.base.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.lgmshare.base.R;
import com.lgmshare.base.http.HttpResponseHandler;
import com.lgmshare.base.http.task.OrderTask;
import com.lgmshare.base.manager.AccountManager;
import com.lgmshare.base.model.ListGroup;
import com.lgmshare.base.model.Order;
import com.lgmshare.base.ui.adapter.NearbyAdapter;
import com.lgmshare.base.ui.fragment.base.BaseReceiverFragment;
import com.lgmshare.base.view.ActionBarLayout;
import com.lgmshare.base.widget.PageLoadingLayout;
import com.lgmshare.base.widget.XListView;
import com.lgmshare.base.widget.XListViewManage;
import com.lgmshare.component.image.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.List;

/**
 * Created with Android Studio.
 * User2 : Lim
 * Email: lgmshare@gmail.com
 * Datetime : 2015/4/28 16:00
 * To change this template use File | Settings | File Templates.
 */
public class NearbyFragment extends BaseReceiverFragment implements AdapterView.OnItemClickListener, XListView.IXListViewListener {

    private ActionBarLayout mActionBarLayout;

    private PageLoadingLayout mPageLoadingLayout;

    private XListView mXListView;
    private int mCurrentPage = 1;
    private NearbyAdapter mAdapter;
    private XListViewManage<Order> mXListViewManage;

    public static NearbyFragment newInstance() {
        NearbyFragment fragment = new NearbyFragment();
        return fragment;
    }

    @Override
    protected int onObtainLayoutResId() {
        return R.layout.fragment_nearby;
    }

    @Override
    protected void initViews() {
        mActionBarLayout = (ActionBarLayout) findViewById(R.id.actionbar);
        mActionBarLayout.setTitle("附近");

        // 加载等待layout
        mPageLoadingLayout = (PageLoadingLayout) findViewById(R.id.layout_loading);
        // 数据错误重载
        mPageLoadingLayout.setErrorButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPageLoadingLayout.showNone();
                onRefresh();
            }
        });
        mPageLoadingLayout.showNone();

        mAdapter = new NearbyAdapter(getActivity());

        mXListView = (XListView) findViewById(R.id.listview);
        mXListView.setPullRefreshEnable(true);//显示刷新
        mXListView.setPullLoadEnable(false);//显示加载更多
        mXListView.setAutoRefreshEnable(true);//开始自动加载
        mXListView.setAutoLoadMoreEnable(true);//滚动到底部自动加载更多
        mXListView.setXListViewListener(this);

        PauseOnScrollListener listener = new PauseOnScrollListener(ImageLoader.getInstance(), true, true);
        mXListView.setOnScrollListener(listener);
        mXListView.setAdapter(mAdapter);

        mXListViewManage = new XListViewManage<Order>();
        mXListViewManage.setXListView(mXListView, mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void broadcastFilter(List<String> actions) {
        actions.add(AccountManager.ACITON_ACCOUNT_INFO_CHANGE_RECEVIER);
    }

    @Override
    protected void broadcastReceiver(String action) {
        if (action.equals(AccountManager.ACITON_ACCOUNT_INFO_CHANGE_RECEVIER)) {

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void httpRequestData(final int mode) {
        if (mode == XListViewManage.MODE_REFRESH) {
            mCurrentPage = 1;
        } else {
            mCurrentPage++;
        }

        OrderTask task = new OrderTask(mCurrentPage, "成都市");
        task.setHttpResponseHandler(new HttpResponseHandler<ListGroup<Order>>() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(ListGroup<Order> lg) {
                if (lg == null){
                    return;
                }
                if (mode == XListViewManage.MODE_REFRESH) {
                    mXListViewManage.onRefresh(lg);
                } else {
                    mXListViewManage.onLoadMore(lg);
                }

                if (mAdapter.getCount() == 0){
                    mPageLoadingLayout.setEmptyMessage(lg.mRespnseMsg);
                    mPageLoadingLayout.showEmpty();
                }
            }

            @Override
            public void onFailure(int code, String error) {
                if (mCurrentPage > 1) {
                    mCurrentPage--;
                }

                if (mAdapter.getCount() == 0){
                    mPageLoadingLayout.setErrorMessage(error);
                    mPageLoadingLayout.showError();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (mode == XListViewManage.MODE_REFRESH) {
                    mXListViewManage.onRefreshComplete();
                } else {
                    mXListViewManage.onLoadMoreComplete();
                }
            }
        });
        task.send(getActivity());
    }

    @Override
    public void onRefresh() {
        httpRequestData(XListViewManage.MODE_REFRESH);
    }

    @Override
    public void onLoadMore() {
        httpRequestData(XListViewManage.MODE_LOAD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
