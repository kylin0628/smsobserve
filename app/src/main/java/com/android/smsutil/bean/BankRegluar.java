package com.android.smsutil.bean;

import android.text.TextUtils;

public class BankRegluar {
    String amount = "error";
    String bankNo = "error";
    private final String BANK_KEY1= "收入";
    private final String BANK_KEY2= "支付宝";
    private final String BANK_KEY3= "财付通";
    private final String BANK_KEY4= "银行";
    String body = "";
    public BankRegluar(String amount, String bankNo,String body) {
        this.amount = amount;
        this.bankNo = bankNo;
        this.body = body;
    }

    public String getAmount() {
        return amount;
    }

    public String getBankNo() {
        return bankNo;
    }

    public boolean isNotBankInfo(){
        return TextUtils.equals(amount,"error")|| TextUtils.equals(bankNo,"error");
    }

    public boolean isMoreBankInfo(){
       return  (!TextUtils.equals(amount,"error")&&!TextUtils.equals(bankNo,"error"))
               ||body.contains(BANK_KEY1)
               ||body.contains(BANK_KEY2)
               ||body.contains(BANK_KEY3)
               ||body.contains(BANK_KEY4);
    }
}
