package com.android.smsutil;

import com.android.smsutil.bean.MsgRspEntity;
import com.android.smsutil.bean.Result;

public interface OnSmsUploadListener {
   void onResult(Result<MsgRspEntity> result);
}
