package com.android.smsutil.bean;

public class SmsReqBean {

    private String account;
    private String from;
    private String datetime;
    private String content;
    private String cardnumber;
    private String token;

    public SmsReqBean(String account, String from, String datetime, String content,String cardnumber,String token) {
        this.account = account;
        this.from = from;
        this.datetime = datetime;
        this.content = content;
        this.cardnumber = cardnumber;
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "SmsReqBean{" +
                "account='" + account + '\'' +
                ", from='" + from + '\'' +
                ", datetime='" + datetime + '\'' +
                ", content='" + content + '\'' +
                ", cardnumber='" + cardnumber + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
