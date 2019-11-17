package com.android.smsutil.bean;

public class MsgRspEntity {
    private String  tradeAmount;
    private String  tradeNo;
    private String  tradeRemark;
    private String  tradeTime;

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeRemark() {
        return tradeRemark;
    }

    public void setTradeRemark(String tradeRemark) {
        this.tradeRemark = tradeRemark;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    @Override
    public String toString() {
        return "MsgRspEntity{" +
                "tradeAmount='" + tradeAmount + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", tradeRemark='" + tradeRemark + '\'' +
                ", tradeTime='" + tradeTime + '\'' +
                '}';
    }
}
