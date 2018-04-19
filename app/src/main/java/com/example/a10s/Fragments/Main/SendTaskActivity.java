package com.example.a10s.Fragments.Main;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a10s.BmobManagers.User;
import com.example.a10s.Fragments.Main.Gsons.RequireGson;
import com.example.a10s.MyView.LoadButton;
import com.example.a10s.MyView.SlipBack;
import com.example.a10s.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class SendTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText receiverView;
    List<RequireGson> requireDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#D0576B"));
        setContentView(R.layout.activity_send_task);
        initView();
        new SlipBack(this);
    }

    private LoadButton sendButton;

    private void initView() {
        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        receiverView = findViewById(R.id.receiverView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendButton:
                if (receiverView.getText().toString() == "") {
                    Toast.makeText(SendTaskActivity.this, "接收者账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendTask();
                break;
        }
    }

    private void sendTask() {
        requireDatas = null;
        sendButton.setLoading(true);
        String receiverUserName = ((EditText) findViewById(R.id.receiverView)).getText().toString();
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("username", receiverUserName);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        Toast.makeText(SendTaskActivity.this, "未找到该用户", Toast.LENGTH_SHORT).show();
                        sendButton.setLoading(false);
                        return;
                    } else {
                        RequireGson requireGson = new RequireGson(
                                ((EditText) findViewById(R.id.titleView)).getText().toString(),
                                User.getCurrentUser().getUsername(),
                                ((EditText) findViewById(R.id.dateView)).getText().toString(),
                                ((EditText) findViewById(R.id.introduceView)).getText().toString()
                        );
                        requireGson.setUsername(receiverUserName);
                        requireGson.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(SendTaskActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(SendTaskActivity.this, "发送失败，重名任务或网络异常", Toast.LENGTH_SHORT).show();
                }
                sendButton.setLoading(false);
            }
        });
    }
}
