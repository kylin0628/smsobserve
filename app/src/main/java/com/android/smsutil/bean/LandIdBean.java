package com.android.smsutil.bean;

/**
 * Created by Administrator on 2019/1/7.
 */

public class LandIdBean {
    private boolean isChoose = false;
    private String id;
    private String landname;
    private String typec;

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLandname() {
        return landname;
    }

    public void setLandname(String landname) {
        this.landname = landname;
    }

    public String getTypec() {
        return typec;
    }

    public void setTypec(String typec) {
        this.typec = typec;
    }
}
