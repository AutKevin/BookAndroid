package com.autumn.receiver;

import com.autumn.bookandroid.MainActivity;
import com.autumn.service.BackService;
import com.autumn.service.NotificationMonitorService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 软件自启代码
 * @author Autumn
 */
public class BroReceiver extends BroadcastReceiver {
	public BroReceiver() {
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {

        //android.widget.Toast.makeText(context, "开机自启成功", android.widget.Toast.LENGTH_SHORT).show();
		//1.创建启动Service的Intent
		Intent intent2 = new Intent(context, BackService.class);
		context.startService(intent2);

		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			Intent intent1=new Intent(context, NotificationMonitorService.class);
			intent1.setAction("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
			context.startService(intent1);  //启动监听服务
		}
	}
}