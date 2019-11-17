package com.android.smsutil;

public class Constants {
    public static  final  int UPLOAD_STATU_PREUPLOAD = 1;
    //升级数据库前的状态值
    public static  final  int UPLOAD_STATU_UPDATE_DB_AHEAD = 0;
    public static  final  int UPLOAD_STATU_SUCCESS = 2;
    public static  final  int UPLOAD_STATU_FAIL = 3;


    public static final int UPLOAD_COMPLETE_STATU_HAVE_SMS = 0XA0;
    public static final int UPLOAD_COMPLETE_STATU_NO_SMS = 0XA1;
    public static final int UPLOAD_COMPLETE_STATU_UPLOADING = 0XA2;
}
