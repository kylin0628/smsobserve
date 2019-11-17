package com.android.smsutil.smsinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.smsutil.R;
import com.android.smsutil.bean.SmsEntity;

import java.util.ArrayList;
import java.util.List;

public class SmsListActivity extends AppCompatActivity implements ISmsListView, AdapterView.OnItemSelectedListener {

    private static final String TAG = "SmsListActivity";
    private SmsListPresenter presenter;
    private Spinner selector;
    private RecyclerView smsRl;
    private List<SmsEntity> smsEntities = new ArrayList<>();
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_list);
        initView();
        presenter = new SmsListPresenter(this);

    }

    private void initView() {
        selector = ((Spinner) findViewById(R.id.sms_list_selector));
        smsRl = ((RecyclerView) findViewById(R.id.sms_list_rl));
        selector.setOnItemSelectedListener(this);
        // 定义一个线性布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        // 设置布局管理器
        smsRl.setLayoutManager(manager);
        // 设置adapter
        adapter = new MessageAdapter(smsEntities);
        smsRl.setAdapter(adapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(TAG, adapterView.getSelectedItem().toString());

        List<SmsEntity> select = presenter.select(i);
        smsEntities.clear();
        if (select != null && select.size() > 0) {

            smsEntities.addAll(select);
        } else {

            Toast.makeText(this, "未查询到数据", Toast.LENGTH_SHORT).show();
        }
        for(int x=0; x<10; x++){
            SmsEntity entity = new SmsEntity();
            entity.setId(Long.valueOf(x));
            entity.setFrom(String.valueOf(x));
            entity.setContent(String.valueOf(x));
            entity.setReceive_time(x);
            entity.setUpload_statu(x);
            entity.setMessage(String.valueOf(x));
            smsEntities.add(entity);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
