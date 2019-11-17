package com.android.smsutil.login;

import java.io.Serializable;

public class LoginEntity implements Serializable {
    public static final String LOGIN_SERIAZIBLE_OBJECT_KEY = "object_login_key";
    private String id;
    private String display;
    private String mobile;
    private String password;
    private String truename;
    private String cardnumber;
    private String bankname;
    private String total;
    private String sum;
    private String status;
    private String t_total;
    private String t_sum;
    private String online;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getT_total() {
        return t_total;
    }

    public void setT_total(String t_total) {
        this.t_total = t_total;
    }

    public String getT_sum() {
        return t_sum;
    }

    public void setT_sum(String t_sum) {
        this.t_sum = t_sum;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "LoginEntity{" +
                "id='" + id + '\'' +
                ", display='" + display + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", truename='" + truename + '\'' +
                ", cardnumber='" + cardnumber + '\'' +
                ", bankname='" + bankname + '\'' +
                ", total='" + total + '\'' +
                ", sum='" + sum + '\'' +
                ", status='" + status + '\'' +
                ", t_total='" + t_total + '\'' +
                ", t_sum='" + t_sum + '\'' +
                ", online='" + online + '\'' +
                '}';
    }
}
