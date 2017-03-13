package com.lgmshare.component.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: lim
 * @description: 解决数字、字母及标点字符提前换行问题
 * @email: lgmshare@gmail.com
 * @datetime 2016/1/18
 */
public class FullTextView extends TextView {

    public FullTextView(Context context) {
        super(context);
    }

    public FullTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FullTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        text = stringFilter(text);
        text = stringToDBC(text);
        super.setText(text, type);
    }

    /**
     * 半角转为全角
     *
     * @param input
     * @return
     */
    public CharSequence stringToDBC(CharSequence input) {
        char[] c = input.toString().toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param input
     * @return
     */
    public CharSequence stringFilter(CharSequence input) {
        // 替换中文标号
        input = input.toString().replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!").replaceAll("：", ":");
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(input);
        return m.replaceAll("").trim();
    }
}
