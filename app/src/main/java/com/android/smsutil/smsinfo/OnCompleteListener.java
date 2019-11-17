package com.android.smsutil.smsinfo;

public interface OnCompleteListener {
    /*
    1 有短信 上传完成   2 没短信  3 正在上传
     */
    void onComplete(int type);
}
