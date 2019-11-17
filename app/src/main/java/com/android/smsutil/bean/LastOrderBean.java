package com.android.smsutil.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Administrator on 2018/9/13.
 * 最近的订单数据
 */

@Entity
public class LastOrderBean {
    @Id
    private Long id;
    @Property(nameInDb = "tradeNo")
    private String tradeNo;

    @Generated(hash = 1023338025)
    public LastOrderBean(Long id, String tradeNo) {
        this.id = id;
        this.tradeNo = tradeNo;
    }

    @Generated(hash = 439212923)
    public LastOrderBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
