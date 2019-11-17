package com.android.smsutil.bean;

/**
 * Created by Administrator on 2018/8/2.
 */

public class AliOrderBean {
    private String order_no;
    private String amount;
    private String myAlipayId;
    private String isLast;

    public String getIsLast() {
        return isLast;
    }

    public void setIsLast(String isLast) {
        this.isLast = isLast;
    }



    public String getMyAlipayId() {
        return myAlipayId;
    }

    public void setMyAlipayId(String myAlipayId) {
        this.myAlipayId = myAlipayId;
    }



    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public AliOrderBean(String order_no, String amount, String myAlipayId, String isLast){
        this.order_no = order_no;
        this.amount = amount;
        this.myAlipayId = myAlipayId;
        this.isLast = isLast;
    }
}
