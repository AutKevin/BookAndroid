package com.autumn.service;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.autumn.pojo.MsgPojo;
import com.autumn.pojo.Users;
import com.autumn.tools.HttpClientUtil;
import com.autumn.tools.JsonUtil;
import com.autumn.bookandroid.MyApplication;
import com.autumn.tools.SharedPreferencesUtils;
import com.google.gson.Gson;

/**
 * 消息监听
 * @author Autumn
 */
public class NotificationMonitorService extends android.service.notification.NotificationListenerService {
    // 在收到消息时触发
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // TODO Auto-generated method stub
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        final String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);
        if(notificationTitle==null&&notificationText==null){   //如果为空,则不执行
        	return;
        }
        //获取登录信息
        Users user = SharedPreferencesUtils.getUserInfo();
        //发送数据
        MsgPojo msgPojo = new MsgPojo();
		msgPojo.setUserName(user.getUsercode());
		msgPojo.setAppName(notificationPkg);
		msgPojo.setTitle(notificationTitle);
		msgPojo.setContext(notificationText);
		Gson gson = new Gson();
		String data = gson.toJson(msgPojo);
        HttpClientUtil.postAndroid(null,data);
        //Log.e("listener_post", "autumn app post: "+notificationPkg+" title: " + notificationTitle + " & " + notificationText);
    }
    
    // 在删除消息时触发
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // TODO Auto-generated method stub
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);
        //Log.e("listener_remove", "autumn app post: "+notificationPkg+" title: " + notificationTitle + " & " + notificationText);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent1=new Intent(MyApplication.getContextObject(), NotificationMonitorService.class);
        intent1.setAction("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        MyApplication.getContextObject().startService(intent1);  //启动监听服务
    }
}