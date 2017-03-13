package com.lgmshare.base.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lgmshare.base.ui.activity.base.BaseReceiverActivity;
import com.lgmshare.component.image.ImageLoader;
import com.lgmshare.base.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/24.
 */
public class TestReceiverActivity extends BaseReceiverActivity {

    @Override
    protected void broadcastFilter(List<String> filters) {
        filters.add("com.lgmshare.base.idoodo");
    }

    @Override
    protected void broadcastReceiver(String filter) {
        TextView textView = (TextView) findViewById(R.id.editText);
        textView.setText(filter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initViews();
    }

    @Override
    protected boolean isHideActionbar() {
        return false;
    }


    protected void initViews() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ATHIS, TestActivity.class));
            }
        });
        StringRequest request = new StringRequest(1, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        ImageView imageView = (ImageView) findViewById(R.id.image);
        ImageLoader.getInstance().displayImage("http://static.adzerk.net/Advertisers/af217662e49a4cbda030feae88418cdd.png", imageView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
