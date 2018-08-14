package com.autumn.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.autumn.bookandroid.MyApplication;
import com.autumn.listener.MyLocationListener;
import com.autumn.tools.NoticeTool;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class BackService extends Service{

	public LocationClient mLocationClient = null;
	private MyLocationListener myListener = new MyLocationListener();
	@Override
	public void onCreate() {

		super.onCreate();
		Intent intent1=new Intent(this, NotificationMonitorService.class);
		intent1.setAction("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
		startService(intent1);   //启动服务
		Log.e("service", "onCreate执行");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mLocationClient = new LocationClient(getApplicationContext());
		//声明LocationClient类
		mLocationClient.registerLocationListener(myListener);

		LocationClientOption option = new LocationClientOption();

		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		//可选，设置定位模式，默认高精度
		//LocationMode.Hight_Accuracy：高精度；
		//LocationMode. Battery_Saving：低功耗；
		//LocationMode. Device_Sensors：仅使用设备；

		option.setCoorType("bd09ll");
		//可选，设置返回经纬度坐标类型，默认gcj02
		//gcj02：国测局坐标；
		//bd09ll：百度经纬度坐标；
		//bd09：百度墨卡托坐标；
		//海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

		option.setScanSpan(1000*5);
		//可选，设置发起定位请求的间隔，int类型，单位ms
		//如果设置为0，则代表单次定位，即仅定位一次，默认为0
		//如果设置非0，需设置1000ms以上才有效

		option.setOpenGps(true);
		//可选，设置是否使用gps，默认false
		//使用高精度和仅用设备两种定位模式的，参数必须设置为true

		option.setLocationNotify(true);
		//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

		option.setIgnoreKillProcess(false);
		//可选，定位SDK内部是一个service，并放到了独立进程。
		//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

		option.SetIgnoreCacheException(false);
		//可选，设置是否收集Crash信息，默认收集，即参数为false

		option.setWifiCacheTimeOut(5*60*1000);
		//可选，7.2版本新增能力
		//如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

		option.setEnableSimulateGps(false);
		//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

		mLocationClient.setLocOption(option);
		//mLocationClient为第二步初始化过的LocationClient对象
		//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
		//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
		option.setIsNeedAddress(true);
		//可选，是否需要地址信息，默认为不需要，即参数为false
		//如果开发者需要获得当前点的地址信息，此处必须为true
		option.setIsNeedLocationDescribe(true);
		//可选，是否需要位置描述信息，默认为不需要，即参数为false
		//如果开发者需要获得当前点的位置信息，此处必须为true
		mLocationClient.setLocOption(option);
		//mLocationClient为第二步初始化过的LocationClient对象
		//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
		//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
		//注册监听函数
		mLocationClient.start();
		//mLocationClient为第二步初始化过的LocationClient对象
		//调用LocationClient的start()方法，便可发起定位请求

		Intent intent1=new Intent(this, NotificationMonitorService.class);
		intent1.setAction("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
		startService(intent1);   //启动服务
		Log.e("service", "onStartCommand执行");
		return super.onStartCommand(intent1, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
        NoticeTool.sendToast("百度SDK关闭");
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}


}
