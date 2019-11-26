package com.android.smsutil;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.carlt.networklibs.NetworkManager;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkManager.getInstance().registerObserver(this);
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

    public boolean hasPermission(String... permission) {
        return EasyPermissions.hasPermissions(this, permission);
    }

    public void requestPermission(String toastStr, int requestCode, String... permissions) {
        EasyPermissions.requestPermissions(this, toastStr, requestCode, permissions);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getInstance().unRegisterObserver(this);
    }
}
