package com.android.smsutil.smsinfo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.smsutil.R;
import com.android.smsutil.bean.SuccessBean;

import java.util.List;

public class MyInfoAdapter extends BaseAdapter {
    List<SuccessBean> data;
    Context context;

    public MyInfoAdapter(Context context, List<SuccessBean> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.item_list, null);
            holder = new ViewHolder();
            holder.id = view.findViewById(R.id.item_id);
            holder.amount = view.findViewById(R.id.item_amount);
            holder.true_amount = view.findViewById(R.id.item_true_amout);
            holder.trade_sn = view.findViewById(R.id.item_trade_sn);
            holder.trade_no = view.findViewById(R.id.item_trade_no);
            holder.card_no = view.findViewById(R.id.item_card_no);
            holder.pay_time = view.findViewById(R.id.item_pay_time);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        SuccessBean successBean = data.get(i);
//        holder.id.setText("id:"+successBean.getId());
        SpannableString ssAmount = new SpannableString("金额:"+successBean.getAmount());
        ForegroundColorSpan fcsAmount = new ForegroundColorSpan(Color.BLUE);
        ssAmount.setSpan(fcsAmount, 3, ssAmount.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        StyleSpan styleSpanAmount  = new StyleSpan(Typeface.BOLD);
        ssAmount.setSpan(styleSpanAmount, 3, ssAmount.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.amount.setText(ssAmount);

        SpannableString ssTrueAmount = new SpannableString("实际支付:"+successBean.getTrue_amount());
        ForegroundColorSpan fcsTrueAmount = new ForegroundColorSpan(Color.BLUE);
        ssTrueAmount.setSpan(fcsTrueAmount, 5, ssTrueAmount.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        StyleSpan styleSpanTrueAmount  = new StyleSpan(Typeface.BOLD);
        ssTrueAmount.setSpan(styleSpanTrueAmount, 5, ssTrueAmount.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.true_amount.setText(ssTrueAmount);

        SpannableString ssTradeSn = new SpannableString("通道订单号:"+successBean.getTrade_sn());
        ForegroundColorSpan fcsTradeSn = new ForegroundColorSpan(Color.BLUE);
        ssTradeSn.setSpan(fcsTradeSn, 6, ssTradeSn.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        StyleSpan styleSpanTradeSn  = new StyleSpan(Typeface.BOLD);
        ssTradeSn.setSpan(styleSpanTradeSn, 6, ssTradeSn.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.trade_sn.setText(ssTradeSn);

        SpannableString ssTradeNo = new SpannableString("平台订单号:"+successBean.getTrade_no());
        ForegroundColorSpan fcsTradeNo = new ForegroundColorSpan(Color.BLUE);
        ssTradeNo.setSpan(fcsTradeNo, 5, ssTradeNo.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        StyleSpan styleSpanTradeNo  = new StyleSpan(Typeface.BOLD);
        ssTradeNo.setSpan(styleSpanTradeNo, 5, ssTradeNo.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.trade_no.setText(ssTradeNo);
//        holder.card_no.setText("card_no:"+successBean.getCard_no());

        SpannableString ssPayTime = new SpannableString("付款时间:"+successBean.getPaytime());
        ForegroundColorSpan fcsPayTime = new ForegroundColorSpan(Color.BLUE);
        ssPayTime.setSpan(fcsPayTime, 4, ssPayTime.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        StyleSpan styleSpanPayTime  = new StyleSpan(Typeface.BOLD);
        ssPayTime.setSpan(styleSpanPayTime, 4, ssPayTime.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.pay_time.setText(ssPayTime);

        return view;
    }

    class ViewHolder {
        private TextView id;
        private TextView amount;
        private TextView true_amount;
        private TextView trade_sn;
        private TextView trade_no;
        private TextView card_no;
        private TextView pay_time;
    }
}
