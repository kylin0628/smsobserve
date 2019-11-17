package com.android.smsutil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

import com.android.smsutil.bean.SmsEntity;
import com.android.smsutil.dao.DaoUtil;

/**
 * Created by Administrator on 2019/1/8.
 */

public class CommandReceiver extends BroadcastReceiver {

    private static final String TAG = "CommandReceiver";
    public static boolean isFirst = true;

    @Override
    public void onReceive(Context context, Intent intent) {
//        ToastUtil.shortToast(context, "hahhahahahh");
        if (isFirst) {
            isFirst = false;
            DaoUtil.init(context);
        }
        //pdus短信单位pdu
        //解析短信内容
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        String number = "";
        StringBuffer msgBody = new StringBuffer();
        long receiveTime = 0;
        for (Object pdu : pdus) {
            //封装短信参数的对象
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);
            number = sms.getOriginatingAddress();
            String messageBody = sms.getMessageBody();
            msgBody.append(messageBody);
            receiveTime = sms.getTimestampMillis();
            Log.e("string", "分段number" + number);
            Log.e("string", "分段body------->" + messageBody);
            Log.e("string", "分段receiveTime" + receiveTime);

        }
        Log.e("string", "合并后的短信number------->" + number);
        Log.e("string", "合并后的短信body------->" + msgBody.toString());
        SmsEntity smsEntity = new SmsEntity(null, number, msgBody.toString(), receiveTime,Constants.UPLOAD_STATU_PREUPLOAD,"");
        DaoUtil.insertSms(smsEntity);
        sendMsg(context, smsEntity);
    }

    //www.sms.broadcast.com
    public static void sendMsg(Context context, SmsEntity smsEntity) {
        Intent intent = new Intent("www.sms.broadcast.com");
        intent.putExtra("smsEntity", smsEntity);
        context.sendBroadcast(intent);
    }

}

