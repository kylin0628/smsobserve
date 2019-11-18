package com.android.smsutil.smsinfo;

import com.android.smsutil.bean.SmsEntity;
import com.android.smsutil.dao.DaoUtil;

import java.util.List;

public class SmsListPresenter {
    SmsListModel model;
    ISmsListView view;

    public SmsListPresenter(ISmsListView view) {
        this.model = new SmsListModel();
        this.view = view;
    }

    /**
     * @param statu 0 全部  1未上传 2 上传成功 3上传失败
     */
    public  List<SmsEntity> select(int statu) {
        List<SmsEntity> smsEntities1 = DaoUtil.queryAllSmsByStatu(statu);
        return smsEntities1;
    }
}
