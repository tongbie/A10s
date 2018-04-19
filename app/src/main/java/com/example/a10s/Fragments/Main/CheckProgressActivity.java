package com.example.a10s.Fragments.Main;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10s.Fragments.Main.Gsons.HomeGson;
import com.example.a10s.MyView.SlipBack;
import com.example.a10s.MyView.datepicker.DPDecor;
import com.example.a10s.MyView.datepicker.bizs.calendars.DPCManager;
import com.example.a10s.MyView.datepicker.views.DatePicker;
import com.example.a10s.R;
import com.example.a10s.Tool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CheckProgressActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressBar progressBar;//进度条
    private TextView progressNum;//进度条进度
    private List<String> dateSign = new ArrayList<>();//标记日期
    private LinearLayout dateLayout;
    private DatePicker picker;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#89C3EB"));
        setContentView(R.layout.activity_check_progress);
        initView();
        SlipBack slipBack=new SlipBack(this);
        slipBack.setFinishY(Tool.dp(120));
    }

    private void initView() {
        findViewById(R.id.queryButton).setOnClickListener(this);
        editText = findViewById(R.id.editText);
        editText.setText("别同");

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressNum = findViewById(R.id.progressNum);
        progressNum.setText(" " + String.valueOf(progressBar.getProgress()) + " %");

        dateLayout = findViewById(R.id.dateLayout);
    }

    private void addData() {
        BmobQuery<HomeGson> query = new BmobQuery<>();
        query.addWhereEqualTo("username", editText.getText().toString());
        query.findObjects(new FindListener<HomeGson>() {
            @Override
            public void done(List<HomeGson> list, BmobException e) {
                if (e == null) {
                    HomeGson data = new HomeGson();
                    if (list.size() == 0) {
                        toast("未查询到该用户");
                        return;
                    }
                    data = list.get(0);
                    progressNum.setText(" " + String.valueOf(data.getProgress()) + " %");
                    progressBar.setProgress(data.getProgress());
                    dateSign = data.getDataSign();
                    addDateSign();
                } else {
                    toast("查找用户失败，请检查网络设置");
                }
            }
        });
    }

    private void addDateSign() {
        dateLayout.removeView(picker);
        picker = new DatePicker(getApplicationContext());
        DPCManager dpcManager = new DPCManager();
        dpcManager.setDecorTR(dateSign);
        picker.setDPCManager(dpcManager);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String date = sdf.format(new java.util.Date());//获取日期
        picker.setDate(Integer.valueOf(date.substring(0, 4)), Integer.valueOf(date.substring(5, 7)));
        picker.setDPDecor(new DPDecor());
        dateLayout.addView(picker);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.queryButton:
                addData();
                break;
        }
    }

    private void toast(String text) {
        try {
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
