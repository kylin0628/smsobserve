package com.android.smsutil.smsinfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.smsutil.R;

class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView fromAddress;
    public TextView upStatu;
    public TextView content;
    public TextView receiveTime;
    public TextView message;

    public MyViewHolder(View itemView) {
        super(itemView);
        fromAddress = itemView.findViewById(R.id.item_message_from_address);
        upStatu = itemView.findViewById(R.id.item_message_statu);
        content = itemView.findViewById(R.id.item_message_body);
        receiveTime = itemView.findViewById(R.id.item_message_receive_time);
        message = itemView.findViewById(R.id.item_message);
    }
}
