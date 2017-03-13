package com.lgmshare.base.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.lgmshare.base.model.DeviceInfo;
import com.lgmshare.base.model.Goods;
import com.lgmshare.base.model.Order;
import com.lgmshare.base.view.OrderListViewItem;

import java.util.List;

/**
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/2/14 10:44
 */
public class NearbyAdapter extends InnerBaseAdapter<Order>{
    private Context mContext;
    public NearbyAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public NearbyAdapter(Context context, List<Order> list) {
        super(context, list);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new OrderListViewItem(mContext);
        }
        OrderListViewItem itemView = (OrderListViewItem) convertView;

        itemView.setData(getItem(position), OrderListViewItem.STATE_FIGHT);
        return convertView;
    }
}
