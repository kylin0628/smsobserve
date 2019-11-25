package com.android.smsutil.login;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.smsutil.BaseActivity;
import com.android.smsutil.BuildConfig;
import com.android.smsutil.HttpUtils;
import com.android.smsutil.MainActivity;
import com.android.smsutil.R;
import com.android.smsutil.SPUtil;
import com.android.smsutil.SmsSource;
import com.android.smsutil.bean.Result;
import com.android.smsutil.dao.DaoUtil;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.io.File;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private Button btn_login;

    EditText et_shanghu_id;
    EditText et_passwd;
    private TextView versionTv;
    private TextView zoneTV;
    private boolean isBeijingZone = true;
    private boolean isHavePer = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        initView();
        initData();
        initOther();
    }

    private void initData() {
        et_shanghu_id.setText((CharSequence) SPUtil.get(this, "et_shanghu_id", ""));
        et_passwd.setText((CharSequence) SPUtil.get(this, "et_passwd", ""));
        getVersionName();

    }

    private void getVersionName() {

        Flowable.just("")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return  HttpUtils.getInstance().getVersionName();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        versionTv.setText(String.format( verText,BuildConfig.VERSION_NAME,s));
                    }
                })
                .onErrorResumeNext(new Publisher<String>() {
                    @Override
                    public void subscribe(Subscriber<? super String> s) {

                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    long lastTime = 0;
    int count = 0;
    String verText = "当前版本号：%1s，服务器版本号：%2s";
    private void initView() {
        et_shanghu_id = findViewById(R.id.et_shanghu_id);
        et_passwd = findViewById(R.id.et_passwd);
        btn_login = findViewById(R.id.btn_login);
        versionTv = findViewById(R.id.version_tv);
        zoneTV = findViewById(R.id.zone_tv);
        versionTv.setText(String.format(verText, BuildConfig.VERSION_NAME,"0.0.0"));
        versionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long cu = System.currentTimeMillis();
                if (cu - lastTime < 2000) {
                    //点击有效
                    count++;
                    if (count > 10) {
                        Log.i("LoginActivity", "触发删除数据库");
                        File file = new File("/sdcard/sms.db");
                        if (file.exists()) {
                            DaoUtil.deleteSms();
                            file.delete();
                            Toast.makeText(LoginActivity.this, "数据库已删除", Toast.LENGTH_SHORT).show();
                        }
                        count = 0;
                    }
                }
                lastTime = cu;
            }
        });
        et_shanghu_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SPUtil.put(LoginActivity.this, "et_shanghu_id", charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_passwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SPUtil.put(LoginActivity.this, "et_passwd", charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isBeijingZone){
                    Toast.makeText(LoginActivity.this, "当前时区不是北京区域", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isHavePer){
                    Toast.makeText(LoginActivity.this, "请开启短信或存储权限，再登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                login();
            }
        });
    }


    private void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Result<LoginEntity> login = HttpUtils.getInstance().login(et_shanghu_id.getText().toString(), et_passwd.getText().toString());
                Log.i("string", "登录信息：" + login.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ("0".equals(login.getCode()) && login.getData() != null) {
                            SPUtil.saveObject(LoginActivity.this, LoginEntity.LOGIN_SERIAZIBLE_OBJECT_KEY, login.getData());
                            SPUtil.put(LoginActivity.this, "phone", login.getData().getMobile());
                            SPUtil.put(LoginActivity.this, "cardNumber", login.getData().getCardnumber());
                            SPUtil.put(LoginActivity.this, "password", login.getData().getPassword());
//
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("loginEntity", login.getData());
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();
    }

    public void initOther() {
        if (hasPermission(Manifest.permission.READ_SMS, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECEIVE_SMS)) {
            try {
                DaoUtil.init(this);
                updateSmsDb();
            } catch (Exception e) {
                String versionLesser = "Can't downgrade database from version";
                if (e.getMessage().contains(versionLesser)) {
                    Toast.makeText(this, "数据库版本太低", Toast.LENGTH_LONG).show();
                }
                e.printStackTrace();
            }

        } else {
            if ("Xiaomi".equals(Build.BRAND)) {
                //小米定制的系统动态权限 无法申请
                isHavePer = false;
                Toast.makeText(this, "小米手机，需手动开启短信和存储权限。", Toast.LENGTH_SHORT).show();

            } else {
                requestPermission("读取短信存储并上传，需要读取短信权限和读写文件权限。", 1000, Manifest.permission.READ_SMS, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECEIVE_SMS);
            }
        }
        isBeijingZone();
    }

    private void isBeijingZone() {
        TimeZone tz = TimeZone.getDefault();
        String strTz = tz.getDisplayName(false, TimeZone.SHORT);
        Log.i(TAG,"时区："+strTz);
        if (!"GMT+08:00".equals(strTz)){
            isBeijingZone = false;
            zoneTV.setText("\n您当前时区并非为北京时区，请先调整时钟。");
            Toast.makeText(this, "您当前时区并非为北京时区，请先调整时钟。", Toast.LENGTH_SHORT).show();
        }else {
            isBeijingZone = true;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
        if (requestCode == 1000) {
            DaoUtil.init(this);
            updateSmsDb();
        }
    }


    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        super.onPermissionsDenied(requestCode, perms);
        if (requestCode == 1000) {
            Toast.makeText(this, "您拒绝了权限，将无法获取短信并上传", Toast.LENGTH_LONG).show();
        }
    }

    private void updateSmsDb() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SmsSource.getInstance().updateSms(LoginActivity.this);
            }
        };
        Thread update = new Thread(runnable);
        update.start();
    }


}
