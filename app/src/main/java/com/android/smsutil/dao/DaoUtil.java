package com.android.smsutil.dao;


import android.content.Context;

import com.android.smsutil.Constants;
import com.android.smsutil.bean.LastOrderBean;
import com.android.smsutil.bean.RecordBean;
import com.android.smsutil.bean.SmsEntity;
import com.anye.greendao.gen.DaoMaster;
import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.LastOrderBeanDao;
import com.anye.greendao.gen.RecordBeanDao;
import com.anye.greendao.gen.SmsEntityDao;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;


/**
 * Created by Administrator on 2017/11/13.
 * 数据库操作
 */

public class DaoUtil {
    private static DaoSession daoSession;

    private static LastOrderBeanDao lastOrderBeanDao;
    private static RecordBeanDao recordBeanDao;
    private static SmsEntityDao smsEntityDao;
    public static final int maxSaveCount = 50;

    public static void init(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new SmsDaoMaster(context, "/sdcard/sms.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
        lastOrderBeanDao = daoSession.getLastOrderBeanDao();
        recordBeanDao = daoSession.getRecordBeanDao();
        smsEntityDao = daoSession.getSmsEntityDao();
    }


    /**
     * 插入记录的数据
     */
    public static void insertRecodeData(String info) {
        if (recordBeanDao.queryBuilder().where(RecordBeanDao.Properties.Info.eq(info)).build().unique() == null) {
            recordBeanDao.insert(new RecordBean(null, info));
        }
    }

    /**
     * 查询
     */
    public static List<RecordBean> queryRecodeList() {
        return recordBeanDao.queryBuilder().build().list();
    }

    /**
     * 删除所有的记录
     */
    public static void delRecodeData() {
        recordBeanDao.deleteAll();
    }


    /**
     * 插入最近的数据
     */
    public static synchronized boolean insertLastOrder(String orderCode) {
        if (lastOrderBeanDao.queryBuilder().where(LastOrderBeanDao.Properties.TradeNo.eq(orderCode)).build().unique() == null) {
            lastOrderBeanDao.insert(new LastOrderBean(null, orderCode));
            return true;
        }
        return false;
    }

    /**
     * 删除最近100条数据
     */
    public static synchronized void delLastOrder() {
        try {
            List<LastOrderBean> list = lastOrderBeanDao.queryBuilder().build().list();
            if (list.size() > maxSaveCount) {
                lastOrderBeanDao.deleteInTx(list.get(0));
            }
        } catch (Exception e) {

        }
    }

    public static synchronized void delLastOrder(String no_) {
        try {
            List<LastOrderBean> list = lastOrderBeanDao.queryBuilder().where(LastOrderBeanDao.Properties.TradeNo.eq(no_)).build().list();
            for (int i = 0; i < list.size(); i++) {
                lastOrderBeanDao.deleteInTx(list.get(i));
            }
        } catch (Exception e) {

        }
    }

    public static void insertSms(SmsEntity smsEntity) {
        smsEntityDao.insert(smsEntity);
    }


    /**
     * 添加多条数据，
     */
    public static void insertSmss(final List<SmsEntity> smss) {
        smsEntityDao.insertInTx(smss);
    }

    public static  List<SmsEntity> queryAllSms() {
        return smsEntityDao.queryBuilder().orderDesc(SmsEntityDao.Properties.Receive_time).build().list();
    }

    /**
     * 根据条件查询短信
     *
     * @param statu 1未上传（没来得及和服务器对话的） 2 上传成功(已经和服务器通了的) 3上传失败（没和服务器通了）
     * @return
     */
    public static  List<SmsEntity> queryAllSmsByStatu(int statu) {
        if (statu <= 0) {
            return queryAllSms();
        }
        WhereCondition eq = null;

        if (statu == 1) {
            //代表要查找未上传的数据。数据库中状态为0 的数据是升级前的，也认为是没上传过的。

//            WhereCondition eq1 = SmsEntityDao.Properties.Upload_statu.eq(statu);
//            WhereCondition eq2 = SmsEntityDao.Properties.Upload_statu.isNull();statu);
//            eq = smsEntityDao.queryBuilder().or(eq1, eq2);
            eq = SmsEntityDao.Properties.Upload_statu.notIn(Constants.UPLOAD_STATU_FAIL, Constants.UPLOAD_STATU_SUCCESS);

        } else {
            eq = SmsEntityDao.Properties.Upload_statu.eq(statu);
        }

        return smsEntityDao.queryBuilder().orderDesc(SmsEntityDao.Properties.Receive_time).where(eq).build().list();
    }

    /**
     * 查询没有上传的短信或失败的。待上传的
     *
     * @return
     */
    public static  List<SmsEntity> queryNeedUpload() {

        WhereCondition eq = SmsEntityDao.Properties.Upload_statu.notIn(Constants.UPLOAD_STATU_SUCCESS);
        return smsEntityDao.queryBuilder().orderDesc(SmsEntityDao.Properties.Receive_time).where(eq).build().list();
    }

    public static synchronized void updateSms(SmsEntity smsEntity) {
        smsEntityDao.update(smsEntity);
    }

    /**
     * 返回最后一条短信的接收时间
     *
     * @return
     */
    public static  long queryLastSmsTime() {
        List<SmsEntity> list = smsEntityDao.queryBuilder().orderDesc(SmsEntityDao.Properties.Receive_time).list();
        if (list != null && list.size() != 0) {
            return list.get(0).getReceive_time();
        }
        return 0;
    }

    /**
     * 删除某一时间点前的短信
     */
    public static  void deleteTime(long time) {
        List<SmsEntity> list = smsEntityDao.queryBuilder().where(SmsEntityDao.Properties.Receive_time.lt(time)).list();
        smsEntityDao.deleteInTx(list);
    }

    public static  List<LastOrderBean> queryLastOrder() {
        return lastOrderBeanDao.queryBuilder().build().list();
    }

    public static String queryLastOrder2Str() {
        List<LastOrderBean> list = lastOrderBeanDao.queryBuilder().build().list();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getTradeNo());
        }
        return sb.toString();
    }
}

