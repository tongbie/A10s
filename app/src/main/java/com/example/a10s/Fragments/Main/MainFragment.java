package com.example.a10s.Fragments.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.a10s.Fragments.Main.CheckTask.CheckTaskActivity;
import com.example.a10s.R;
import com.example.a10s.Tool;

/**
 * Created by BieTong on 2018/1/22.
 */

public class MainFragment extends Fragment implements View.OnClickListener {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main, null);
            initView();
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        Tool.scaleAnimation(view, R.id.animateLayout);
        return view;
    }

    private void initView() {
        view.findViewById(R.id.sendTaskButton).setOnClickListener(this);
        view.findViewById(R.id.sendTaskLayout).setOnClickListener(this);
        view.findViewById(R.id.checkProgressButton).setOnClickListener(this);
        view.findViewById(R.id.checkProgressLayout).setOnClickListener(this);
        view.findViewById(R.id.checkTaskButton).setOnClickListener(this);
        view.findViewById(R.id.checkTaskLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendTaskButton:
            case R.id.sendTaskLayout:
                startActivity(new Intent(getActivity(), SendTaskActivity.class));
                break;
            case R.id.checkProgressButton:
            case R.id.checkProgressLayout:
                startActivity(new Intent(getActivity(), CheckProgressActivity.class));
                break;
            case R.id.checkTaskButton:
            case R.id.checkTaskLayout:
                startActivity(new Intent(getActivity(), CheckTaskActivity.class));
                break;
        }
    }
}
