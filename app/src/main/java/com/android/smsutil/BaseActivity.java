package com.android.smsutil;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //授权成功

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //授权失败
    }

    public boolean hasPermission(String ...permission){
        return EasyPermissions.hasPermissions(this,permission);
    }

    public void requestPermission(String toastStr,int requestCode,String ...permissions){
        EasyPermissions.requestPermissions(this, toastStr, requestCode, permissions);
    }
}
