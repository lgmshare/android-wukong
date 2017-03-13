package com.lgmshare.component.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

/**
 * 图片加载封装类库
 *
 * @author lim
 * @description: TODO
 * @email lgmshare@gmail.com
 * @datetime 2016/11/21 11:21
 */
public class ImageLoader {

    public static void load(Context context, ImageView view, String url) {
        if (url.contains(".gif")) {
            loadGif(context, view, url);
        } else {
            Glide.with(context).load(url)
                    .into(view);
        }
    }

    public static void loadGif(Context context, ImageView view, String url) {
        if (!url.contains(".gif")) {
            load(context, view, url);
        } else {
            Glide.with(context).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new GlideDrawableImageViewTarget(view, 0));
        }
    }

    public static void loadPlaceholder(Context context, ImageView view, String url, int ph) {
        Glide.with(context).load(url)
                .centerCrop()
                .placeholder(ph)
                .into(view);
    }

    public static void loadSize(Context context, ImageView view, String url, int w, int h) {
        Glide.with(context).load(url)
                .centerCrop()
                .override(w, h)
                .into(view);
    }

    /**
     * @param context
     * @param view    目标imageView
     * @param url     图片url
     * @param w       图片宽度
     * @param h       图片高度
     * @param ph      占位图
     */
    public static void loadSize(Context context, ImageView view, String url, int w, int h, int ph) {
        Glide.with(context).load(url)
                .centerCrop()
                .override(w, h)
                .placeholder(ph)
                .into(view);
    }
}
