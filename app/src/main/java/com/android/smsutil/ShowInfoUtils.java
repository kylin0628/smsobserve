package com.android.smsutil;

import android.content.Context;
import android.content.Intent;

import com.android.smsutil.dao.DaoUtil;

/**
 * Created by Administrator on 2018/9/28.
 * 在mainactivity中显示数据
 */

public class ShowInfoUtils {
    public static void  SendDaota(Context context, String info){
        try {
            Intent intent = new Intent("www.msgactivity.com");
            info = StringUtils.timeStamp2Date(String.valueOf(System.currentTimeMillis()/1000), "MM-dd-HH:mm:ss")+ ":  "+info;
            intent.putExtra("info", info);
            intent.putExtra("type", "notify");
            context.sendBroadcast(intent);
        }catch (Exception e){

        }

    }

    public static void saveInfo(String info){
        info = StringUtils.timeStamp2Date(String.valueOf(System.currentTimeMillis()/1000), "MM-dd-HH:mm:ss")+ ":  "+info;
        DaoUtil.insertRecodeData(info);
    }

}
