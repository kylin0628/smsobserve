package com.android.smsutil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.smsutil.bean.Result;
import com.android.smsutil.bean.SuccessBean;
import com.android.smsutil.dao.DaoUtil;
import com.android.smsutil.login.LoginEntity;
import com.android.smsutil.service.MyService;
import com.android.smsutil.smsinfo.MyInfoAdapter;
import com.android.smsutil.smsinfo.OnCompleteListener;
import com.android.smsutil.smsinfo.SmsListActivity;
import com.android.smsutil.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    public Button btn_start;
    public ListView lv_info;
    MyInfoAdapter infoAdapter;
    private TextView tv_current_landid;
    private Button onKeyUpload;
    private Button onKeyDelete;
    private LoginEntity loginEntity;
    private MyBroadCast broadCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initBroadCast();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadCast);
    }

    private void initBroadCast() {
        if (broadCast == null) {
            broadCast = new MyBroadCast();
            IntentFilter filter = new IntentFilter("www.msgactivity.com");
            this.registerReceiver(broadCast, filter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {
        loginEntity = (LoginEntity) getIntent().getSerializableExtra("loginEntity");
        if (loginEntity != null) {
            String text = "姓名:%1s\n手机号:%2s\n银行名称:%3s\n银行卡号:%4s";
            tv_current_landid.setText(String.format(text, loginEntity.getTruename(), loginEntity.getMobile(), loginEntity.getBankname(), loginEntity.getCardnumber()));
        }
        getSuccessList(0);
        // Android 8.0使用startForegroundService在前台启动新服务
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(MainActivity.this, MyService.class));
        } else {
            startService(new Intent(MainActivity.this, MyService.class));
        }
    }

    private List<SuccessBean> data = new ArrayList<>();

    private void getSuccessList(int second) {
        Flowable.just("")
                .delay(second, TimeUnit.SECONDS)
                .map(new Function<String, Result<List<SuccessBean>>>() {
                    @Override
                    public Result<List<SuccessBean>> apply(String s) throws Exception {

                        return HttpUtils.getInstance().getSuccessList(loginEntity.getMobile(), loginEntity.getPassword());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Result<List<SuccessBean>>>() {
                    @Override
                    public void accept(Result<List<SuccessBean>> listResult) throws Exception {
                        if (listResult.getCode().equals("0")) {
                            data.clear();
                            data.addAll(listResult.getData());
                            infoAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MainActivity.this, listResult.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .subscribe();


    }

    private void initView() {
        btn_start = findViewById(R.id.btn_see_sms);
        tv_current_landid = findViewById(R.id.tv_current_landid);
        lv_info = findViewById(R.id.lv_info);
        onKeyUpload = findViewById(R.id.on_key_upload);
        onKeyDelete = findViewById(R.id.on_key_delete);


        btn_start.setOnClickListener(this);
        onKeyUpload.setOnClickListener(this);
        onKeyDelete.setOnClickListener(this);
        infoAdapter = new MyInfoAdapter(this, data);
        lv_info.setAdapter(infoAdapter);
    }


    /**
     * {"code":200,"msg":"\u83b7\u53d6\u6210\u529f","data":[{"id":"503","userid":"185","username":"13011112222","typec":"1","sdk":"847ba9a1a6bbc83128dec4df38"},{"id":"733","userid":"185","username":"1111","typec":"1","sdk":"be0367ebfe20c470f7a948b348"},{"id":"732","userid":"185","username":"13129812183","typec":"3","sdk":"9845ac2c7478332f861995cd5c"}]}
     *
     * @param view
     */

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_see_sms:
                startActivity(new Intent(this, SmsListActivity.class));
                break;
            case R.id.on_key_upload:
                uploadSmss();
                break;
            case R.id.on_key_delete:
                deleteSmss();
                break;
        }
    }

    private void uploadSmss() {
        onKeyUpload.setText("正在上传");
        onKeyUpload.setClickable(false);
        SmsSource.getInstance().uploadAllSms(MainActivity.this, new OnCompleteListener() {
            @Override
            public void onComplete(final int type) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onKeyUpload.setText("一键上传");
                        onKeyUpload.setClickable(true);
                        if (type == Constants.UPLOAD_COMPLETE_STATU_HAVE_SMS) {

                            Toast.makeText(MainActivity.this, "上传完成", Toast.LENGTH_LONG).show();
                        } else if (type == Constants.UPLOAD_COMPLETE_STATU_NO_SMS) {

                            Toast.makeText(MainActivity.this, "没有短信可上传", Toast.LENGTH_LONG).show();
                        } else if (type == Constants.UPLOAD_COMPLETE_STATU_UPLOADING) {

                            Toast.makeText(MainActivity.this, "正在上传中", Toast.LENGTH_LONG).show();
                        }
                        getSuccessList(10);
                    }
                });
            }
        });

    }


    private void deleteSmss() {

        AsyncTask<Void, Void, Void> deleteTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                onKeyDelete.setText("正在删除");
                onKeyDelete.setClickable(false);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                DaoUtil.deleteTime(DateUtils.getTody0Oclick());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(MainActivity.this, "00点前的短信删除完毕", Toast.LENGTH_LONG).show();
                onKeyDelete.setText("一键删除");
                onKeyDelete.setClickable(true);
            }
        };
        deleteTask.execute();
    }

    private class MyBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //收到了单词短信上传完成。
            Log.i(TAG, "收到了单词短信上传完成");
            getSuccessList(10);
        }
    }

}