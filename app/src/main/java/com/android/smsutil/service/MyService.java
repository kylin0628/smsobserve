package com.android.smsutil.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.smsutil.HttpUtils;
import com.android.smsutil.R;
import com.android.smsutil.SPUtil;
import com.android.smsutil.SmsSource;
import com.android.smsutil.bean.SmsEntity;
import com.android.smsutil.smsinfo.OnCompleteListener;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2017/7/29.
 */

public class MyService extends Service {

    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private Notification notification = null;
    private MyBroadCast broadCast;
    private static int NOTIFICATION_ID = 0x2333;
    public static int pauseTime = 1;  //暂停时间
    private int timerDelay = 1;  //计时器的计时间隔
    private final static int STOPTASK = 1;  //没有定位数据了，停止任务
    private static ExecutorService fixedThreadPool = Executors.newCachedThreadPool();

    private static boolean isFirst = true;
    private static int running = 0;
    private static int count = 0;

    private static boolean isRuning_zfb = true;
    private static boolean isRuning_wx = true;

    private static boolean isCheckZfb = false;
    private static boolean isCheckWx = false;

    private Notification mNotification;

    NotificationManager manager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("string", "onUnbind------------>");
        return super.onUnbind(intent);
    }


    /**
     * 开启前台服务
     */
    void setForeground() {
        mNotification = initNotification();
        startForeground(NOTIFICATION_ID, mNotification);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            stopTimer();
            initTimer();
            registerBroadCast();
            setForeground();
        } catch (Exception e) {

        }
        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("string", "重启------------>");
        unRegisterBroadCast();
        // 重启自己
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        startService(intent);
    }

    /**
     * 处理handler数据
     */
    private void hanlderData(Message msg) {

    }



    void initTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    count += 1;
                    if (count % 10 == 0) {
//                        Toast.makeText(MyService.this, "短信监控中...", Toast.LENGTH_SHORT).show();
                        toast("短信监控中...");
                    }


                    if (count % 60 == 0) {
                        //一分钟上传同步一次短信库
//                        if (EasyPermissions.hasPermissions(MyService.this, Manifest.permission.READ_SMS, Manifest.permission.READ_EXTERNAL_STORAGE,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECEIVE_SMS)) {
//                            SmsSource.getInstance().updateSms(MyService.this);
//                            SmsSource.getInstance().uploadAllSms(MyService.this, new OnCompleteListener() {
//                                @Override
//                                public void onComplete() {
//
//                                }
//                            });
//                        } else {
//                            toast("没有短信或者存储权限，无法同步和上传短信");
//                        }

                    }
                    if (count%3600==0){
                        //1小时登录续期
                        String acount = (String) SPUtil.get(getApplicationContext(), "phone", "");
                        HttpUtils.getInstance().loginGoon(acount);
                    }
                }

            };
            mTimer.schedule(mTimerTask, 1000, 1000);
        }
    }


    /**
     * 停止timer
     */
    private void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;

        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }


    /**
     * 通知栏
     *
     * @return
     */
    private Notification initNotification() {
        if (notification == null) {
            notification = new Notification.Builder(this.getApplicationContext()).
                    setContentTitle("").
                    setContentText("支付管家").
                    setSmallIcon(R.mipmap.app_icon).
                    setWhen(System.currentTimeMillis()).build();
        }
        return notification;
    }


    /**
     * 注册广播
     */
    private void registerBroadCast() {
        if (broadCast == null) {
            IntentFilter intentFilter = new IntentFilter("www.sms.broadcast.com");
            broadCast = new MyBroadCast();
            MyService.this.registerReceiver(broadCast, intentFilter);
        }
    }


    /**
     * 关闭广播
     */
    private void unRegisterBroadCast() {
        MyService.this.unregisterReceiver(broadCast);
        broadCast = null;
    }

    /**
     * 广播用来上传的广播。
     */
    private class MyBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            SmsEntity smsEntity = (SmsEntity) intent.getSerializableExtra("smsEntity");
            String acount = (String) SPUtil.get(getApplicationContext(), "phone", "");
            String cardNumber = (String) SPUtil.get(getApplicationContext(), "cardNumber", "");
            String password = (String) SPUtil.get(getApplicationContext(), "password", "");
//            LoginEntity login = (LoginEntity) SPUtil.readObject(context, LoginEntity.LOGIN_SERIAZIBLE_OBJECT_KEY);
            if (TextUtils.isEmpty(cardNumber)) {
                Toast.makeText(context, "未登录，不能上传", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.i("SERVER", "收到银行相关信息，上传：" + smsEntity.toString());
            SmsSource.getInstance().uploadSms(smsEntity, acount, cardNumber, password, new OnCompleteListener() {
                @Override
                public void onComplete(int type) {
                    Intent intent = new Intent("www.msgactivity.com");
                    sendBroadcast(intent);
                }
            });
        }
    }


    public void toast(final String str) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyService.this, str, Toast.LENGTH_LONG).show();
            }
        });
    }


}
