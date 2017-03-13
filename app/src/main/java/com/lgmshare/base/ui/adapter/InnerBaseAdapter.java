package com.lgmshare.base.ui.adapter;

import android.content.Context;

import com.lgmshare.component.ui.adapter.FrameAdapter;

import java.util.List;

/**
 * @author: lim
 * @description: TODO
 * @email: lgmshare@gmail.com
 * @datetime 2016/2/1
 */
public abstract class InnerBaseAdapter<T> extends FrameAdapter<T> {

    public InnerBaseAdapter(Context context) {
        super(context);
    }

    public InnerBaseAdapter(Context context, List<T> list) {
        super(context, list);
    }
}
