package com.lgmshare.component.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;


/**
 * @author lim
 * @Description: 设置字体样式
 * @mail lgmshare@gmail.com
 * @date 2014年6月9日  上午10:37:00
 */
public class TextStyleUtils {

    private SpannableString mSpannableString;

    public static TextStyleUtils newInstance(String str) {
        return new TextStyleUtils(str);
    }

    private TextStyleUtils() {
    }

    private TextStyleUtils(String str) {
        mSpannableString = new SpannableString(str);
    }

    public void setString(String str) {
        mSpannableString = new SpannableString(str);
    }

    public SpannableString getSpannableString() {
        return mSpannableString;
    }

    /**
     * @param size  相对文字大小
     * @param start
     * @param end
     * @return
     */
    public TextStyleUtils setRelativeSize(float size, int start, int end) {
        if (mSpannableString == null) {
            return this;
        }
        // 0.5f表示默认字体大小的一半
        mSpannableString.setSpan(new RelativeSizeSpan(size), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * @param size  绝对文字大小
     * @param start
     * @param end
     * @param dp
     * @return
     */
    public TextStyleUtils setAbsoluteSize(int size, int start, int end, boolean dp) {
        if (mSpannableString == null) {
            return this;
        }
        // 第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
        mSpannableString.setSpan(new AbsoluteSizeSpan(size, dp), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 设置文字颜色
     *
     * @param Color
     * @param start
     * @param end
     * @return
     */
    public TextStyleUtils setForegroundColor(int Color, int start, int end) {
        if (mSpannableString == null) {
            return this;
        }
        // 设置前景色为洋红色
        mSpannableString.setSpan(new ForegroundColorSpan(Color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 文字背景色
     *
     * @param Color
     * @param start
     * @param end
     * @return
     */
    public TextStyleUtils setBackgroundColor(int Color, int start, int end) {
        if (mSpannableString == null) {
            return this;
        }
        // 设置前景色为洋红色
        mSpannableString.setSpan(new BackgroundColorSpan(Color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public static void setFakeBold(TextView textView, boolean isBold) {
        TextPaint tp = textView.getPaint();
        tp.setFakeBoldText(isBold);
    }

    /**
     * 设置下划线
     */
    public TextStyleUtils setUnderlineSpan(int start, int end) {
        if (mSpannableString == null) {
            return this;
        }
        // 设置前景色为洋红色
        mSpannableString.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 设置删除线
     */
    public TextStyleUtils setStrikethroughSpan(int start, int end) {
        if (mSpannableString == null) {
            return this;
        }
        // 设置前景色为洋红色
        mSpannableString.setSpan(new StrikethroughSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public TextStyleUtils setSubscriptSpan(int start, int end) {
        if (mSpannableString == null) {
            return this;
        }
        // 设置前景色为洋红色
        mSpannableString.setSpan(new SubscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public TextStyleUtils setSuperscriptSpan(int start, int end) {
        if (mSpannableString == null) {
            return this;
        }
        // 设置前景色为洋红色
        mSpannableString.setSpan(new SuperscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }
}