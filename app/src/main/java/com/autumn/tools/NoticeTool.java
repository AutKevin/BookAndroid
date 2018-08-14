package com.autumn.tools;

import android.app.Notification;
import android.app.NotificationManager;
import android.widget.Toast;

import com.autumn.bookandroid.MyApplication;
import com.autumn.bookandroid.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NoticeTool {
    public static void sendNotice(){
        //NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }
    public static void sendToast(String text) {
        Toast.makeText(MyApplication.getContextObject(), text, Toast.LENGTH_SHORT).show();
    }
}
