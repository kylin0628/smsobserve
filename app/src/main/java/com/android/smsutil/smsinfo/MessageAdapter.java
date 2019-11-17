package com.android.smsutil.smsinfo;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smsutil.Constants;
import com.android.smsutil.R;
import com.android.smsutil.bean.SmsEntity;
import com.android.smsutil.utils.DateUtils;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {
    private List<SmsEntity> smss ;

    public MessageAdapter(List<SmsEntity> smss) {
        this.smss = smss;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            ((MyViewHolder) holder).fromAddress.setText(smss.get(position).getFrom());
            ((MyViewHolder) holder).content.setText(smss.get(position).getContent());
            ((MyViewHolder) holder).receiveTime.setText(DateUtils.getStringTime(smss.get(position).getReceive_time()));
            int upload_statu = smss.get(position).getUpload_statu();
            String text = "未知";
            String color = "#000000";
            switch (upload_statu){
                case Constants.UPLOAD_STATU_SUCCESS:
                    text = "上传成功";
                    color = "#00ee00";
                    break;
                case Constants.UPLOAD_STATU_FAIL:
                    text = "上传失败";
                    color = "#ee0000";
                    break;
                case Constants.UPLOAD_STATU_PREUPLOAD:
                case Constants.UPLOAD_STATU_UPDATE_DB_AHEAD:
                    text = "未上传";
                    color = "#ffa100";
                    break;
            }
            ((MyViewHolder) holder).upStatu.setText(text);
            ((MyViewHolder) holder).upStatu.setTextColor(Color.parseColor(color));
            ((MyViewHolder) holder).message.setText(smss.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return smss.size();
    }
}
