package com.lgmshare.base.ui.activity.common;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.lgmshare.base.AppConfig;
import com.lgmshare.base.AppConstants;
import com.lgmshare.base.R;
import com.lgmshare.base.ui.activity.base.BaseActivity;
import com.lgmshare.base.view.ActionBarLayout;
import com.lgmshare.component.logger.Logger;

import java.io.File;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/11/11 16:54
 * @email: lgmshare@gmail.com
 */
public class WebViewActivity extends BaseActivity {

    protected ActionBarLayout mActionBarLayout;

    protected int mPageTtitle;
    protected String mPageUrl;

    protected ProgressBar mProgressBar;
    protected WebView mWebView;

    @Override
    protected boolean isHideActionbar() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initIntentExtras();
        initActionBar();
        initWebSeting();
        overridePendingTransition(R.anim.slide_in_right, R.anim.none);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.none, R.anim.slide_out_right);
    }

    private void initIntentExtras() {
        mPageTtitle = getIntent().getIntExtra(AppConstants.EXTRA_WEB_TITLE, R.string.app_name);
        mPageUrl = getIntent().getStringExtra(AppConstants.EXTRA_WEB_URL);
    }

    private void initActionBar() {
        mActionBarLayout = new ActionBarLayout(this);
        mActionBarLayout.setTitle(mPageTtitle, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.this.finish();
            }
        });
        mActionBar.setCustomView(mActionBarLayout);
    }

    private void initWebSeting() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setMax(100);

        mWebView = (WebView) findViewById(R.id.webView);
        //支持缩放
        mWebView.getSettings().setSupportZoom(true);
        //设置支持缩放显示工具栏
        mWebView.getSettings().setBuiltInZoomControls(false);
        //将图片调整到适合webview的大小
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBlockNetworkImage(false);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUserAgentString("Android");
        //编码方式
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        //默认字体大小
        mWebView.getSettings().setDefaultFontSize(16);
        //支持js
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(mClient);
        mWebView.setWebChromeClient(mChromeClient);

        mWebView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mWebView.loadUrl(mPageUrl);
    }

    /**
     * 当我们加载Html时候，会在我们data/应用package下生成database与cache两个文件夹:
     * 我们请求的Url记录是保存在webviewCache.db里，而url的内容是保存在webviewCache文件夹下.
     * WebView中存在着两种缓存：网页数据缓存（存储打开过的页面及资源）、H5缓存（即AppCache）。
     * <p/>
     * 一、网页缓存
     * <p/>
     * 1、缓存构成
     * /data/data/package_name/cache/
     * /data/data/package_name/database/webview.db
     * /data/data/package_name/database/webviewCache.db
     * <p/>
     * 综合可以得知 webview 会将我们浏览过的网页url已经网页文件(css、图片、js等)保存到数据库表中
     * 缓存模式(5种)
     * LOAD_CACHE_ONLY:  不使用网络，只读取本地缓存数据
     * LOAD_DEFAULT:  根据cache-control决定是否从网络上取数据。
     * LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
     * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
     * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
     * 如：www.taobao.com的cache-control为no-cache，在模式LOAD_DEFAULT下，无论如何都会从网络上取数据，如果没有网络，就会出现错误页面；
     * 在LOAD_CACHE_ELSE_NETWORK模式下，无论是否有网络，只要本地有缓存，都使用缓存。本地没有缓存时才从网络上获取。
     * www.360.com.cn的cache-control为max-age=60，在两种模式下都使用本地缓存数据。
     * <p/>
     * 总结：根据以上两种模式，建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT，无网络时，使用LOAD_CACHE_ELSE_NETWORK。
     */
    protected void openWebViewCache() {
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.NORMAL);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        //开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + AppConfig.FILE_CACHE_PATH;
        Logger.d(TAG, "cacheDirPath=" + cacheDirPath);
        //设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        //设置 Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(true);
    }

    /**
     * 清除WebView缓存
     */
    protected void clearWebViewCache() {
        //清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath() + AppConfig.FILE_CACHE_PATH);
        Logger.d(TAG, "appCacheDir path=" + appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath() + "/webviewCache");
        Logger.d(TAG, "webviewCacheDir path=" + webviewCacheDir.getAbsolutePath());
        //删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
        //删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    private void deleteFile(File file) {
        Logger.i(TAG, "delete file path=" + file.getAbsolutePath());
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Logger.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }

    /**
     * 设置本地调用对象及其接口
     *
     * @param object
     * @param alias
     */
    protected void addJavascriptInterface(Object object, String alias) {
        mWebView.addJavascriptInterface(object, alias);
    }

    private WebChromeClient mChromeClient = new WebChromeClient() {

        public void onProgressChanged(WebView view, int progress) {
            // Activities and WebViews measure progress with different scales.
            // The progress meter will automatically disappear when we reach 100%
            Logger.d(TAG, "progress：" + progress);
            mProgressBar.setProgress(progress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mActionBarLayout.setTitle(title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Logger.e(TAG, "onJsAlert " + message);
            result.confirm();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            Logger.e(TAG, "onJsConfirm " + message);
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            Logger.e(TAG, "onJsPrompt " + url);
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

    };

    private WebViewClient mClient = new WebViewClient() {

        @Override
        public void onLoadResource(WebView view, String url) {
            Logger.d(TAG, "onLoadResource url=" + url);
            super.onLoadResource(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            Logger.d(TAG, "shouldOverrideUrlLoading url=" + url);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Logger.d(TAG, "onPageStarted");
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Logger.d(TAG, "onPageFinished");
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Logger.d(TAG, "onReceivedError");
            mProgressBar.setVisibility(View.GONE);
        }
    };

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        if (mWebView != null) {
            mWebView.stopLoading();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.loadUrl("about:blank");
            /*setBuiltInZoomControls(true) 引发的crash 这个方法调用以后 如果你触摸屏幕 弹出那个提示框还没消失的时候
            你如果activity结束了 就会报错了。3.0以上 4.4以下很多手机会出现这种情况.
            所以为了规避他，我们通常是在activity的onDestroy方法里手动的将webiew设置成 setVisibility(View.GONE)*/
            mWebView.setVisibility(View.GONE);
        }
        super.onDestroy();
    }
}
