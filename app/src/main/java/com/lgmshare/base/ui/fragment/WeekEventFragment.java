package com.lgmshare.base.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.lgmshare.base.AppApplication;
import com.lgmshare.base.AppConstants;
import com.lgmshare.base.AppContext;
import com.lgmshare.base.R;
import com.lgmshare.base.helper.PathConfigruationHelper;
import com.lgmshare.base.http.HttpRequestURL;
import com.lgmshare.base.manager.AccountManager;
import com.lgmshare.base.model.User;
import com.lgmshare.base.ui.activity.common.WebViewSelectImageActivity;
import com.lgmshare.base.ui.activity.image.ImageSelectActivity;
import com.lgmshare.base.ui.dialog.ActionSheetDialog;
import com.lgmshare.base.ui.dialog.WheelDialog;
import com.lgmshare.base.ui.fragment.base.BaseReceiverFragment;
import com.lgmshare.component.image.ImageLoader;
import com.lgmshare.component.logger.Logger;
import com.lgmshare.component.network.volley.MultiPartRequest;
import com.lgmshare.component.network.volley.RequestParams;
import com.lgmshare.component.network.volley.ResponseCallback;
import com.lgmshare.component.network.volley.ResponseParser;
import com.lgmshare.component.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/9/30 18:02
 * @email: lgmshare@gmail.com
 */
public class WeekEventFragment extends BaseReceiverFragment implements AdapterView.OnItemClickListener {

    public static NearbyFragment newInstance(String url) {
        NearbyFragment fragment = new NearbyFragment();
        Bundle args = new Bundle();
        args.putString("mLoadUrl", url);
        fragment.setArguments(args);
        return fragment;
    }

    TextView textView;

    @Override
    protected int onObtainLayoutResId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initViews() {
        textView = (TextView) findViewById(R.id.textview);
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionSheetDialog dialog = new ActionSheetDialog(getActivity());
                dialog.setTitle("符合");
                dialog.addSheetItem("ddd", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        ToastUtil.show(getActivity(), position + "");
                    }
                });

                String[] sss = new String[]{"1", "2", "3"};
                dialog.addStringArrayItem(sss, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        ToastUtil.show(getActivity(), position + "");
                    }
                });
                dialog.show();
            }
        });
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<String>();
                list.add("111111111");
                list.add("222222222");
                list.add("333333333");
                list.add("444444444");
                list.add("555555555");
                list.add("666666666");
                list.add("777777777");
                list.add("888888888");
                final WheelDialog dialog = new WheelDialog(getActivity());
                dialog.setTitle("选中");
                dialog.setDefaultStringArrayAdapter(list, R.layout.adapter_wheelview_item, R.id.tv_title);
                dialog.setOnEnsureClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebViewSelectImageActivity.class);
                intent.putExtra(AppConstants.EXTRA_WEB_TITLE, R.string.image_select_crop_img);
                intent.putExtra(AppConstants.EXTRA_WEB_URL, "http://www5.53kf.com/webCompany.php?arg=9004061&style=1");
                getActivity().startActivity(intent);
                /*BMessage baseMessage = new BMessage();
                baseMessage.setType(2);
                baseMessage.setContent("sssssssssssssssss");
                BaseApplication.getInstance().getMessageManager().displayMessage(baseMessage);*/
            }
        });
        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ImageSelectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstants.EXTRA_IMAGE_CROP, false);
                bundle.putString(AppConstants.EXTRA_IMAGE_PATH, PathConfigruationHelper.getFilePathToCache(getActivity(), "temp", "png"));
                intent.putExtras(bundle);
                startActivityForResult(intent, 200);

            }
        });
    }

    private void uploadImage(String path) {
        JSONObject json = new JSONObject();
        try {
            json.put("reqName", HttpRequestURL.C1);
            json.put("pdatype", HttpRequestURL.INTERFACE_PDATYPE);
            json.put("version", HttpRequestURL.INTERFACE_VERSION);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, String> headers = HttpRequestURL.createCommonHeader();
        String params = HttpRequestURL.getSecretParams(headers.get("timestamp"), json.toString());
        RequestParams requestParams = new RequestParams();
        requestParams.put("json", params);
        try {
            requestParams.put("name", new File(path), MultiPartRequest.CONTENT_TYPE_IMG);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Logger.d(requestParams.toString());
        MultiPartRequest<String> request = new MultiPartRequest(Request.Method.POST, HttpRequestURL.URL_C, requestParams, new ResponseParser() {
            @Override
            public Object parse(String data) {
                return data;
            }
        }, new ResponseCallback() {

            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailure(int code,String error) {

            }
        });
        request.setHeader("UDID", headers.get("UDID"));
        request.setHeader("User-Agent", headers.get("User-Agent"));
        request.setHeader("timestamp", headers.get("timestamp"));
        request.setHeader("Accept", headers.get("Accept"));
        request.setHeader("USERNAME", headers.get("USERNAME"));
        request.setHeader("TOKEN", headers.get("TOKEN"));
        AppContext.getRequestQueue().add(request);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void broadcastFilter(List<String> actions) {
        actions.add(AccountManager.ACITON_ACCOUNT_INFO_CHANGE_RECEVIER);
        actions.add("com.lgmshare.base.account.test");
    }

    @Override
    protected void broadcastReceiver(String action) {
        Logger.d(TAG, "broadcastReceiver:" + action);
        if (action.equals(AccountManager.ACITON_ACCOUNT_INFO_CHANGE_RECEVIER)) {
            ImageView imageview = (ImageView) findViewById(R.id.imageview);
            User user = AppApplication.getInstance().getAccountManager().getUser();
            if (user != null)
                ImageLoader.getInstance().displayImage(user.getHeadimgurl(), imageview);
        } else if (action.equals("com.lgmshare.base.account.test")) {
            textView.setText("test");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String imagePath = data.getStringExtra("image-path");
            Logger.d(TAG, imagePath);
            Uri uri = data.getParcelableExtra("image-path-uri");
            ImageView imageview = (ImageView) findViewById(R.id.imageview);
            ImageLoader.getInstance().displayImage(Uri.decode(uri.toString()), imageview);
            uploadImage(imagePath);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
