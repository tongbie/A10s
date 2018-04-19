package com.example.a10s.Fragments.Main.CheckTask;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10s.BmobManagers.User;
import com.example.a10s.MyView.RefreshButton;
import com.example.a10s.MyView.SlipBack;
import com.example.a10s.R;
import com.example.a10s.Fragments.Main.Gsons.RequireGson;
import com.example.a10s.Tool;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class CheckTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout listLayout;
    private RefreshButton refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#FFB11B"));
        setContentView(R.layout.activity_check_task);
        initView();
        addData();
        new SlipBack(this);
    }

    private void initView() {
        listLayout = findViewById(R.id.listLayout);
        refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(this);
    }

    private void addData() {
        refreshButton.setRefreshing(true);
        listLayout.removeAllViews();
        BmobQuery<RequireGson> query = new BmobQuery<>();
        query.addWhereEqualTo("sender", User.getCurrentUser(User.class).getUsername());
        query.findObjects(new FindListener<RequireGson>() {
            @Override
            public void done(List<RequireGson> list, BmobException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        toast("任务列表为空");
                    } else {
                        for (RequireGson data : list) {
                            listLayout.addView(new TaskItem(
                                    getApplicationContext(),
                                    data.getTitle(),
                                    data.getDate(),
                                    data.getSender(),
                                    data.getIntroduce(),
                                    data.getState()) {
                                String objectId = data.getObjectId();

                                @Override
                                public void resend() {
                                    data.setState(2);
                                    data.update(objectId, new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                toast("已重置发送状态");
                                                addData();
                                            } else {
                                                toast("操作失败，请重试");
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void delete() {
                                    data.delete(objectId, new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                toast("已删除");
                                                addData();
                                            } else {
                                                toast("删除失败");
                                            }
                                        }
                                    });
                                }
                            });
                        }
                        TextView space = new TextView(getApplicationContext());
                        space.setHeight(Tool.requireItemHeight);
                        listLayout.addView(space);
                    }
                } else {
                    toast("查询任务列表失败，请检查网络设置");
                }
                refreshButton.setRefreshing(false);
            }
        });
    }

    private void toast(String text) {
        try {
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refreshButton:
                addData();
                break;
        }
    }
}
