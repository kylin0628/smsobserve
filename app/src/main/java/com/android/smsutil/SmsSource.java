package com.android.smsutil;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.smsutil.bean.MsgRspEntity;
import com.android.smsutil.bean.Result;
import com.android.smsutil.bean.SmsEntity;
import com.android.smsutil.dao.DaoUtil;
import com.android.smsutil.smsinfo.OnCompleteListener;
import com.android.smsutil.utils.DateUtils;
import com.android.smsutil.utils.RegularUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SmsSource {
    /*
    content://sms/inbox        收件箱
    content://sms/sent        已发送
    content://sms/draft        草稿
    content://sms/outbox        发件箱
    content://sms/failed        发送失败
    content://sms/queued        待发送列表

    _id               一个自增字段，从1开始
thread_id    序号，同一发信人的id相同
address      发件人手机号码
person        联系人列表里的序号，陌生人为null
date            发件日期
protocol      协议，分为： 0 SMS_RPOTO, 1 MMS_PROTO
read           是否阅读 0未读， 1已读
status         状态 -1接收，0 complete, 64 pending, 128 failed
type
    ALL    = 0;
    INBOX  = 1;
    SENT   = 2;
    DRAFT  = 3;
    OUTBOX = 4;
    FAILED = 5;
    QUEUED = 6;
body                     短信内容
service_center     短信服务中心号码编号
subject                  短信的主题

     */

    private static Uri SMS_INBOX = Uri.parse("content://sms/inbox");
    private String TAG = "SmsSource";
    private static SmsSource instance = null;

    private SmsSource() {
    }

    public static SmsSource getInstance() {
        synchronized (SmsSource.class) {
            if (instance == null) {
                instance = new SmsSource();
            }
        }
        return instance;
    }

    ExecutorService executorService = Executors.newScheduledThreadPool(3);

    /**
     * 某一个时间点之后的收到的短信
     *
     * @param context
     * @param afterTime
     * @param isRegularBank 是否要过滤银行短信
     * @return
     */

    private List<SmsEntity> obtainPhoneMessage(Context context, long afterTime, boolean isRegularBank) {
        ContentResolver cr = context.getContentResolver();
        List<SmsEntity> smss = new ArrayList<>();
        String[] projection = new String[]{"_id", "address", "body", "date"};
        Cursor cur = cr.query(SMS_INBOX, projection, "date>?", new String[]{String.valueOf(afterTime)}, "date desc");
        if (null == cur) {
            Log.i("ooc", "************cur == null");
            return smss;
        }
        while (cur.moveToNext()) {
            String body = cur.getString(cur.getColumnIndex("body"));//短信内容
            if (isRegularBank) {
                if (RegularUtil.getBankRegEntity(body).isMoreBankInfo()) {
                    String number = cur.getString(cur.getColumnIndex("address"));//手机号
                    long date = cur.getLong(cur.getColumnIndex("date"));//收到时间
                    smss.add(new SmsEntity(null, number, body, date, Constants.UPLOAD_STATU_PREUPLOAD, ""));
                }
            } else {
                String number = cur.getString(cur.getColumnIndex("address"));//手机号
                long date = cur.getLong(cur.getColumnIndex("date"));//收到时间
                smss.add(new SmsEntity(null, number, body, date, Constants.UPLOAD_STATU_PREUPLOAD, ""));
            }

        }
        return smss;
    }


    /**
     * 将收件箱中最新的短信同步到本地。
     *
     * @param context
     */
    public void updateSms(final Context context) {
        Log.i(TAG, "同步短信开始");
        //只同步1小时内的短信，超过1小时就没用了
        long lastSmsTime = System.currentTimeMillis()-60*60*1000;
        //最后一条短信的时间
        long lastSmsTime1 = DaoUtil.queryLastSmsTime();
        Log.i(TAG, "lastSmsTime:" + DateUtils.getStringTime(lastSmsTime));
        //找到所有未同步的短信
       long tempStam = Math.max(lastSmsTime,lastSmsTime1);
        List<SmsEntity> smsEntities = obtainPhoneMessage(context, tempStam, false);
        //从未同步的短信中筛选出近1小时ne
        DaoUtil.insertSmss(smsEntities);
        Log.i(TAG, "同步短信结束");
        Log.i(TAG, "总共同步到" + smsEntities.size() + "条短信");

    }

    /**
     * 将本地数据库中未同步成功的短信同步上去。
     * @param context
     */
    public void uploadAllSms(Context context) {
        Log.i(TAG, "开始上传所有本地短信");
        if (count>0) return;
        List<SmsEntity> smsEntities = DaoUtil.queryNeedUpload();
        if (smsEntities != null && smsEntities.size() > 0) {
            //当前登录的手机号
            String acount = (String) SPUtil.get(context.getApplicationContext(), "phone", "");
            String cardNumber = (String) SPUtil.get(context.getApplicationContext(), "cardNumber", "");
            String password = (String) SPUtil.get(context.getApplicationContext(), "password", "");
//            LoginEntity login = (LoginEntity) SPUtil.readObject(context, LoginEntity.LOGIN_SERIAZIBLE_OBJECT_KEY);
            if (TextUtils.isEmpty(cardNumber)) {
                Toast.makeText(context, "未登录，不能上传", Toast.LENGTH_SHORT).show();
                return;
            }
            for (SmsEntity smsEntity : smsEntities) {
                uploadSms(smsEntity, acount, cardNumber, password, new OnCompleteListener() {
                    @Override
                    public void onComplete(int type) {

                    }
                });
            }
        }
    }

    volatile int  count = 0;

    /**
     * 将本地数据库中未同步成功的短信同步上去。
     * @param context
     */
    public void uploadAllSms(Context context, final OnCompleteListener listener) {
        Log.i(TAG, "开始上传所有本地短信");
        if (count>0){
            listener.onComplete(Constants.UPLOAD_COMPLETE_STATU_UPLOADING);
            return;
        }
        count = 0;
        List<SmsEntity> smsEntities = DaoUtil.queryNeedUpload();
        if (smsEntities != null && smsEntities.size() > 0) {
            //当前登录的手机号
            String acount = (String) SPUtil.get(context.getApplicationContext(), "phone", "");
            String cardNumber = (String) SPUtil.get(context.getApplicationContext(), "cardNumber", "");
            String password = (String) SPUtil.get(context.getApplicationContext(), "password", "");
//            LoginEntity login = (LoginEntity) SPUtil.readObject(context, LoginEntity.LOGIN_SERIAZIBLE_OBJECT_KEY);
            if (TextUtils.isEmpty(cardNumber)) {
                Toast.makeText(context, "未登录，不能上传", Toast.LENGTH_SHORT).show();
                return;
            }
            count = smsEntities.size();
            Log.i(TAG,"上传短信总数："+count);
            for (SmsEntity smsEntity : smsEntities) {
                uploadSms(smsEntity, acount, cardNumber, password, new OnCompleteListener() {
                    @Override
                    public void onComplete(int type) {
                        reduce(listener);
                    }


                });
            }
        }else {
            listener.onComplete(Constants.UPLOAD_COMPLETE_STATU_NO_SMS);
        }
    }

    private synchronized void reduce( OnCompleteListener listener) {
        count--;
        Log.i(TAG,"成功+1,当前count:"+count);
        if (count<=0){
            listener.onComplete(Constants.UPLOAD_COMPLETE_STATU_HAVE_SMS);
            count=0;
        }
    }
    public void uploadSms(final SmsEntity smsEntity, String acount,
                          String cardNumber, String password, final OnCompleteListener listener) {

        executorService.submit(new SmsUploadRunnable(acount, smsEntity, cardNumber, password, new OnSmsUploadListener() {
            @Override
            public void onResult(Result<MsgRspEntity> result) {
                smsEntity.setMessage(result.getMessage());
                if (!TextUtils.isEmpty(result.getCode())) {
                    smsEntity.setUpload_statu(Constants.UPLOAD_STATU_SUCCESS);
                } else {
                    smsEntity.setUpload_statu(Constants.UPLOAD_STATU_FAIL);
                }
                DaoUtil.updateSms(smsEntity);
                listener.onComplete(Constants.UPLOAD_COMPLETE_STATU_HAVE_SMS);
            }
        }));
    }
}
