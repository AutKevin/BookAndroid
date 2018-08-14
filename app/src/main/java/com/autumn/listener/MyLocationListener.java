package com.autumn.listener;


import android.util.Log;

import com.autumn.pojo.GPSPojo;
import com.autumn.pojo.Users;
import com.autumn.tools.HttpClientUtil;
import com.autumn.tools.JsonUtil;
import com.autumn.bookandroid.MyApplication;
import com.autumn.tools.SharedPreferencesUtils;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyLocationListener extends BDAbstractLocationListener {
    @Override
    public void onReceiveLocation(BDLocation location){

        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        //以下只列举部分获取经纬度相关（常用）的结果信息
        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

        double latitude = location.getLatitude();    //获取纬度信息
        double longitude = location.getLongitude();    //获取经度信息
        float radius = location.getRadius();    //获取定位精度，默认值为0.0f
        String addr = location.getAddrStr();    //获取详细地址信息
        String country = location.getCountry();    //获取国家
        String province = location.getProvince();    //获取省份
        String city = location.getCity();    //获取城市
        String district = location.getDistrict();    //获取区县
        String street = location.getStreet();    //获取街道信息
        String coorType = location.getCoorType();
        //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
        String locationDescribe = location.getLocationDescribe();    //获取位置描述信息

        int errorCode = location.getLocType();


        Log.e("我的位置坐标position",latitude+","+longitude+","+addr+","+locationDescribe);

        //获取登录信息
        Users user = SharedPreferencesUtils.getUserInfo();
        Log.i("notice读取个人信息成功",user.toString());
        if((addr!=null||locationDescribe!=null)&&user!=null&&user.getId()!=null){
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            GPSPojo gpsPojo = new GPSPojo();
            gpsPojo.setLontitude(longitude+"");
            gpsPojo.setLatitude(latitude+"");
            gpsPojo.setAddr(addr);
            gpsPojo.setLocationdescribe(locationDescribe);
            gpsPojo.setUserId(user.getId());
            gpsPojo.setUserName(user.getName());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(new Date());
            gpsPojo.setTime(time);

            JsonUtil<GPSPojo> jsonUtil = new JsonUtil<GPSPojo>();
            HttpClientUtil.postAndroid("http://www.52zt.online:8088/Bookkeeping/HttpInfoController/getGPS",jsonUtil.objectToJSON(gpsPojo));
        }
    }

}
