package com.android.smsutil.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class SmsEntity implements Serializable {
    @Id(autoincrement = true)
    private Long id;
    private String from;
    private String content;
    private long receive_time;
    //上传状态 0 以前的数据认为没有未上传 1 未上传  2 上传成功  3 上传失败
    private int upload_statu;
    private String message="";
    

    @Generated(hash = 1038963764)
    public SmsEntity(Long id, String from, String content, long receive_time,
            int upload_statu, String message) {
        this.id = id;
        this.from = from;
        this.content = content;
        this.receive_time = receive_time;
        this.upload_statu = upload_statu;
        this.message = message;
    }


    @Generated(hash = 1127714058)
    public SmsEntity() {
    }



    @Override
    public String toString() {
        return "SmsEntity{" +
                ", from='" + from + '\'' +
                ", content='" + content + '\'' +
                ", receive_time=" + receive_time +
                '}';
    }


    public long getReceive_time() {
        return this.receive_time;
    }


    public void setReceive_time(long receive_time) {
        this.receive_time = receive_time;
    }


    public String getContent() {
        return this.content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public String getFrom() {
        return this.from;
    }


    public void setFrom(String from) {
        this.from = from;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }




    public int getUpload_statu() {
        return this.upload_statu;
    }


    public void setUpload_statu(int upload_statu) {
        this.upload_statu = upload_statu;
    }


    public String getMessage() {
        return this.message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public boolean equals( Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
       return this.toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
