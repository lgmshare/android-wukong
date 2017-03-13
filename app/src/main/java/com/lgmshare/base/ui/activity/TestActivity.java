package com.lgmshare.base.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lgmshare.base.R;
import com.lgmshare.base.model.User;
import com.lgmshare.base.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/28.
 */
public class TestActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User u = new User();
            u.setName("111"+i);
            u.setMobile("1111111111111");
            users.add(u);
        }
        myRecyclerAdapter = new MyRecyclerAdapter(users);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(myRecyclerAdapter);
    }

    @Override
    protected boolean isHideActionbar() {
        return false;
    }


    class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<User> userList;

        public MyRecyclerAdapter(List<User> list) {
            userList = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_item, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            User user = userList.get(position);
            holder.tv_name.setText(user.getName());
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_exception);
        }
    }
}
