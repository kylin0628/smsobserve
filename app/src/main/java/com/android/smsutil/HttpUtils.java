package com.android.smsutil;

import android.util.Log;

import com.android.smsutil.bean.MsgRspEntity;
import com.android.smsutil.bean.Result;
import com.android.smsutil.bean.SmsReqBean;
import com.android.smsutil.bean.SuccessBean;
import com.android.smsutil.login.LoginEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/7/28.
 */

public class HttpUtils {
//    public static String baseUrl = "http://mq.test.wamkj.com/";
    /**
     * ------------------->MNEfK8TDgmILxoqISIk6LJOUUitO2RxK
     * public static String baseSocketUrl = "ws://120.78.206.36/";
     * 09-06 12:27:09.911 10520-10520/com.android.zfbpaykeeplive E/string: ------------------->7K50smujLPlM+9SlCRwAOg==
     * 09-06 12:27:09.911 10520-10520/com.android.zfbpaykeeplive E/string: ------------------->7K50smujLPnSckKjI+A2oDW4ZtjhUvhy
     * 09-06 12:27:09.912 10520-10520/com.android.zfbpaykeeplive E/string: ------------------->7K50smujLPnREvtttkZ6iFbxG0Pzzqdn
     * 09-06 12:27:09.912 10520-10520/com.android.zfbpaykeeplive E/string: ------------------->oSmo3tCTK+hQ8rLCBABiQIgI4/mJu86Kzzx3/rxLNoQ=
     * 09-06 12:27:09.912 10520-10520/com.android.zfbpaykeeplive E/string: ------------------->wb9MbxP77OfIQrwMmpzZ7eK8CD95qVDeowISxGZexr0=
     */


    //    public static String baseUrl = "http://api.hxx13581983709.top/";
//    public static String URL_LOGIN = baseUrl+"?a=servlet&b=index&c=applogin";    //登陆
//    public static String HOST1 = "http://47.75.135.101";
    public static String HOST1 = "http://47.244.223.45";
    //登录接口
    public static String URL_LOGIN = HOST1 + "/Api/App/Login";    //登陆
    //获取版本号接口
    public static String URL_VERSION_NAME = HOST1 + "/Api/App/version";
    //http://47.75.135.101/Api/App/UploadSms 短信上传接口
    public static String URL_UPLOAD_MESSAGE = HOST1 + "/Api/App/UploadSms";
    //http://47.75.135.101/Api/App/UpdateAccountOnlineTime/?username=13987680001&debug=1 续期
    public static String URL_LOGIN_CONNITU = HOST1 + "/Api/App/UpdateAccountOnlineTime";
    public static String URL_GET_PAY_DATA = HOST1 + "/Api/App/SuccessList";

    private static final String TAG = "HttpUtils";
    private static HttpUtils instance = null;
    Gson gson = new Gson().newBuilder().create();

    private HttpUtils() {
    }


    public static HttpUtils getInstance() {
        synchronized (HttpUtils.class) {
            if (instance == null) {
                instance = new HttpUtils();
            }
        }
        return instance;
    }


    /**
     * 上传短信接口。 规定 code 有值 代表成功， code没值代表不成功
     * @param smsReqBean
     * @return
     */
    public Result<MsgRspEntity> sendMsg(SmsReqBean smsReqBean) {
        Result<MsgRspEntity> result = new Result<>();
        try {
            Response response = OkHttpUtils.post().url(URL_UPLOAD_MESSAGE)
                    .addParams("data", gson.toJson(smsReqBean))
                    .build().execute();
            if (response.isSuccessful()) {
                Type type = new TypeToken<Result<MsgRspEntity>>() {
                }.getType();
                String string = response.body().string();
                Log.i(TAG, "请求url：" + URL_UPLOAD_MESSAGE);
                Log.i(TAG, "请求参数：smsReqBean--" + gson.toJson(smsReqBean));
                Log.i(TAG, "请求结果：" + string);
                result = gson.fromJson(string, type);
            }

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Result<LoginEntity> login(String username, String password) {
        Result result = new Result();
        try {
            Response execute = OkHttpUtils.post()
                    .url(URL_LOGIN)
                    .addParams("username", username)
                    .addParams("password", password)
                    .build().execute();
            if (execute.code() == 200) {
                //请求成功
                String string = execute.body().string();
                Log.i(TAG, "请求url：" + URL_LOGIN);
                Log.i(TAG, "请求参数：" + username + "---" + password);
                Log.i(TAG, "请求结果：" + string);
                JSONObject object = new JSONObject(string);
                if ("0".equals(object.optString("code"))) {
                    Type type = new TypeToken<Result<LoginEntity>>() {
                    }.getType();
                    result = gson.fromJson(string, type);
                } else {
                    result.setMessage(object.optString("message"));
                    result.setCode(object.optString("code"));
                }


            } else {
                //网络请求失败
                result.setCode("-1");
                result.setMessage("网络请求失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getVersionName() {
        String versionName = "";
        try {
            Response execute = OkHttpUtils.get().url(URL_VERSION_NAME).build().execute();
            versionName = execute.body().string();
            Log.i(TAG, "请求url：" + URL_VERSION_NAME);
            Log.i(TAG, "请求结果：" + versionName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public Result<LoginEntity> loginGoon(String phone) {
        Result result = new Result();
        try {
            Response execute = OkHttpUtils.post()
                    .url(URL_LOGIN_CONNITU)
                    .addParams("username", phone)
                    .build().execute();
            if (execute.code() == 200) {
                //请求成功
                String string = execute.body().string();
                Log.i(TAG, "请求url：" + URL_LOGIN_CONNITU);
                Log.i(TAG, "请求参数：" + phone);
                Log.i(TAG, "请求结果：" + string);
                Type type = new TypeToken<Result<LoginEntity>>() {
                }.getType();
                result = gson.fromJson(string, type);

            } else {
                //网络请求失败
                result.setCode("-1");
                result.setMessage("网络请求失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.setMessage("登陆失败");
        }

        return result;
    }

    public Result<List<SuccessBean>> getSuccessList(String phone, String password) {
        Result result = new Result();
        try {
            Response execute = OkHttpUtils.post()
                    .url(URL_GET_PAY_DATA)
                    .addParams("username", phone)
                    .addParams("password", password)
                    .build().execute();
            if (execute.code() == 200) {
                //请求成功
                String string = execute.body().string();
                Log.i(TAG, "请求url：" + URL_LOGIN_CONNITU);
                Log.i(TAG, "请求参数：" + phone);
                Log.i(TAG, "请求结果：" + string);
                Type type = new TypeToken<Result<List<SuccessBean>>>() {
                }.getType();
                result = gson.fromJson(string, type);

            } else {
                //网络请求失败
                result.setCode("-1");
                result.setMessage("网络请求失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
