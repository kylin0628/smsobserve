package com.android.smsutil.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Administrator on 2018/9/28.
 * 所有的发送消息，接受消息记录
 */
@Entity
public class RecordBean {
    @Id
    private Long id;
    @Property(nameInDb = "info")
    private String info;

    @Generated(hash = 2044687025)
    public RecordBean(Long id, String info) {
        this.id = id;
        this.info = info;
    }

    @Generated(hash = 96196931)
    public RecordBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
