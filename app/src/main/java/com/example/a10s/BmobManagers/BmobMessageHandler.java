package com.example.a10s.BmobManagers;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**
 * Created by BieTong on 2018/3/5.
 */

public class BmobMessageHandler extends BmobIMMessageHandler {
    public static List<MessageEvent> events = new ArrayList<>();
    public static List<OfflineMessageEvent> offEvents = new ArrayList<>();


    @Override
    public void onMessageReceive(final MessageEvent event) {
//        BmobIMConversation conversation = event.getConversation();
//        BmobIM.getInstance().updateConversation(conversation);
    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //离线消息，每次connect的时候会查询离线消息，如果有，此方法会被调用
        /*List<MessageEvent> bMessageEvents = new ArrayList<>();
        Set<Map.Entry<String, List<MessageEvent>>> entrySet = event.getEventMap().entrySet();
        Iterator<Map.Entry<String, List<MessageEvent>>> it = entrySet.iterator();
        while (it.hasNext()) {
            bMessageEvents = it.next().getValue();
        }
        for (int i = 0; i < bMessageEvents.size(); i++) {
            BmobIM.getInstance().updateConversation(bMessageEvents.get(i).getConversation());
            BmobIM.getInstance().updateUserInfo(bMessageEvents.get(i).getFromUserInfo());
        }*/
    }

    public void updateUserInfo(MessageEvent event) {
        BmobIMConversation bConversation = event.getConversation();
        BmobIMUserInfo bInfo = event.getFromUserInfo();
//        BmobIMMessage bMessage = event.getMessage();
        BmobIM.getInstance().updateConversation(bConversation);
        BmobIM.getInstance().updateUserInfo(bInfo);
    }
}
