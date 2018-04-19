package com.example.a10s.Fragments.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a10s.R;
import com.example.a10s.Tool;

import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;

/**
 * Created by BieTong on 2018/2/9.
 */

public class ConversationAdapter extends ArrayAdapter {
    private List<BmobIMConversation> conversations;
    private Context context;

    public ConversationAdapter(Context context, int resourseId, List<BmobIMConversation> conversations) {
        super(context, resourseId, conversations);
        this.context = context;
        this.conversations = conversations;
    }

    public List<BmobIMConversation> getConversations() {
        return conversations;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.message_item, viewGroup, false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
            viewHolder.nameView = (TextView) view.findViewById(R.id.nameView);
            viewHolder.messageView = (TextView) view.findViewById(R.id.messageView);
            view.setTag(viewHolder);//setTag()用以向View追加额外数据
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        BmobIMConversation conversation = (BmobIMConversation) getItem(position);
        int size = conversation.getMessages().size();
        viewHolder.imageView.setImageResource(Tool.imageId);
        viewHolder.nameView.setText(conversation.getConversationTitle());
        viewHolder.messageView.setText(conversation.getDraft());
        return view;
    }

    public class ViewHolder {
        public ImageView imageView;
        public TextView nameView;
        public TextView messageView;
    }
}
