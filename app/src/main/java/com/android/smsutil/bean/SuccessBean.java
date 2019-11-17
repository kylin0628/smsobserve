package com.android.smsutil.bean;

import java.io.Serializable;

public class SuccessBean implements Serializable {

          private String id;
          private String amount;
          private String true_amount;
          private String trade_sn;
          private String trade_no;
          private String card_no;
          private String paytime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTrue_amount() {
        return true_amount;
    }

    public void setTrue_amount(String true_amount) {
        this.true_amount = true_amount;
    }

    public String getTrade_sn() {
        return trade_sn;
    }

    public void setTrade_sn(String trade_sn) {
        this.trade_sn = trade_sn;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }
}
