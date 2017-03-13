package com.lgmshare.base.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lgmshare.base.R;
import com.lgmshare.base.model.Advertisement;
import com.lgmshare.component.image.ImageLoader;
import com.lgmshare.component.image.ImageLoaderOption;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class HomeAdvImageAdapter extends InnerBaseAdapter<Advertisement> {

    private DisplayImageOptions mDisplayImageOptions;

    public HomeAdvImageAdapter(Context context) {
        super(context);
        mDisplayImageOptions = ImageLoaderOption.getDisplayImageOptions(R.drawable.ic_launcher);
    }

    @Override
    public int getCount() {
        if (getList().size() == 0)
            return 0;
        return Integer.MAX_VALUE; // 返回很大的值使得getView中的position不断增大来实现循环
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.adapter_home_ads_image_item, parent, false);
        }
        if (getCount() == 0) {
            return convertView;
        }
        Advertisement adv = getDataItem(position);
        ImageView imageView = ((ImageView) convertView.findViewById(R.id.imgView));
        ImageLoader.getInstance().displayImage(adv.getImage(), imageView, mDisplayImageOptions);
        imageView.setTag(adv.getUrl());
        return convertView;
    }

    private Advertisement getDataItem(int position){
        return getList().get(position % getList().size());
    }
}
