package com.autumn.bookandroid;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.autumn.pojo.Users;
import com.autumn.service.BackService;
import com.autumn.service.NotificationMonitorService;
import com.autumn.tools.HttpClientUtil;
import com.autumn.tools.JsonUtil;
import com.autumn.tools.NoticeTool;
import com.autumn.tools.SharedPreferencesUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getPersimmions();


        //创建启动地图Service的Intent
        final Intent intent = new Intent(MainActivity.this, BackService.class);
        startService(intent);

        Intent intent_listener=new Intent(this, NotificationMonitorService.class);
        intent_listener.setAction("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        startService(intent_listener);  //启动监听后台服务

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置账号密码框登录的值

    }

    @Override
    protected void onStart() {
        super.onStart();String userName = SharedPreferencesUtils.getParam(MyApplication.getContextObject(),"userName","").toString();
        String pwd = SharedPreferencesUtils.getParam(MyApplication.getContextObject(),"pwd","").toString();
        EditText userName_EditText =(EditText)findViewById(R.id.userName);
        EditText pwd_EditText =(EditText)findViewById(R.id.psw);
        userName_EditText.setText(userName.toCharArray(),0,userName.length());
        pwd_EditText.setText(pwd.toCharArray(),0,pwd.length());

    }

    public void onClick1(View view) {
        String string = Settings.Secure.getString(getContentResolver(),
                "enabled_notification_listeners");
        if (!string.contains(NotificationMonitorService.class.getName())) {
            Toast.makeText(MainActivity.this, "未获得权限,请选择允许权限", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, "已经获得权限", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        startActivity(intent);// 来授权  会跳到授权界面
    }


    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(
                        permissions.toArray(new String[permissions.size()]),
                        SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList,
                                  String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
    //登录
    public void onClick_Login(View view) {
        EditText userName =(EditText)findViewById(R.id.userName);
        EditText pwd =(EditText)findViewById(R.id.psw);
        SharedPreferencesUtils.setParam(MyApplication.getContextObject(),"userName",userName.getText().toString());
        SharedPreferencesUtils.setParam(MyApplication.getContextObject(),"pwd",pwd.getText().toString());

        //String userName_result = SharedPreferencesUtils.getParam(MyApplication.getContextObject(),"userName","").toString();
        //String pwd_result = SharedPreferencesUtils.getParam(MyApplication.getContextObject(),"pwd","").toString();
        Users user = new Users();
        user.setUsercode(userName.getText().toString());
        user.setPwd(pwd.getText().toString());
        //登录,返回详细信息
        String userStr = HttpClientUtil.postObjectAndroidByCall(HttpClientUtil.URL_LOGIN,user);

        JsonUtil<Users> jsonUtil1 = new JsonUtil<Users>();
        Users userInfo = jsonUtil1.jsonToObject(userStr,Users.class);
        if(userInfo!=null&&userInfo.getId()!=null&&!userInfo.getId().trim().isEmpty()){
            //将相信信息存入SharedPreferences
            SharedPreferencesUtils.setParam(MyApplication.getContextObject(),"user",userStr);
            NoticeTool.sendToast(userInfo.getName()+"登录成功");
            Log.i("保存个人信息成功",userStr);
        }else{
            NoticeTool.sendToast("登录失败,账户名或密码不对");
        }
    }

}
