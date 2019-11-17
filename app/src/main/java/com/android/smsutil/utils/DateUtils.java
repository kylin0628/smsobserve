package com.android.smsutil.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述:公共日期工具类
 */
public class DateUtils {


    public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


    static SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
    /**
     * 获取今日0点的时间戳
     * @return
     */
    public static long getTody0Oclick(){
        String stringTime = DateUtils.getStringTime(System.currentTimeMillis());
        String[] split = stringTime.split(" ");
        String dian0 = split[0]+" 00:00:00";
        return DateUtils.getTimestamp(dian0);
    }

    /**
     * @desc 字符串转时间戳
     * @param  time
     * @example time="2019-04-19 00:00:00"
     **/
    public static Long getTimestamp(String time) {
        Long timestamp = null;
        try {
            timestamp =df.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * @desc  时间戳转字符串
     * @param  timestamp
     * @example timestamp=1558322327000
     **/
    public static String getStringTime(Long timestamp) {
        String datetime = df.format(timestamp);  // 获取年月日时分秒
        return datetime;
    }


    public static String dateToDateTime(Date date) {
        String datestr = null;
        datestr = df.format(date);
        return datestr;
    }

}