package com.android.smsutil;

import com.android.smsutil.bean.MsgRspEntity;
import com.android.smsutil.bean.Result;
import com.android.smsutil.bean.SmsEntity;
import com.android.smsutil.bean.SmsReqBean;
import com.android.smsutil.utils.DateUtils;
import com.android.smsutil.utils.Encript;

public class SmsUploadRunnable implements Runnable {
    private String acount;
    private String cardNumber;
    private String password;
    private SmsEntity smsEntity;
    private OnSmsUploadListener listener;

    public SmsUploadRunnable(String acount, SmsEntity smsEntity,String cardNumber,String password,OnSmsUploadListener listener) {
        this.acount = acount;
        this.smsEntity = smsEntity;
        this.listener = listener;
        this.cardNumber = cardNumber;
        this.password = password;
    }

    @Override
    public void run() {
        //网络请求
        SmsReqBean smsReqBean = new SmsReqBean(acount, smsEntity.getFrom(),
                DateUtils.getStringTime(smsEntity.getReceive_time()),
                smsEntity.getContent(),
                cardNumber, Encript.md5(cardNumber+password));
        Result<MsgRspEntity> aBoolean = HttpUtils.getInstance().sendMsg(smsReqBean);
        listener.onResult(aBoolean);
    }
}
