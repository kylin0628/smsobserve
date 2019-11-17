package com.anye.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.android.smsutil.bean.LastOrderBean;
import com.android.smsutil.bean.RecordBean;
import com.android.smsutil.bean.SmsEntity;

import com.anye.greendao.gen.LastOrderBeanDao;
import com.anye.greendao.gen.RecordBeanDao;
import com.anye.greendao.gen.SmsEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig lastOrderBeanDaoConfig;
    private final DaoConfig recordBeanDaoConfig;
    private final DaoConfig smsEntityDaoConfig;

    private final LastOrderBeanDao lastOrderBeanDao;
    private final RecordBeanDao recordBeanDao;
    private final SmsEntityDao smsEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        lastOrderBeanDaoConfig = daoConfigMap.get(LastOrderBeanDao.class).clone();
        lastOrderBeanDaoConfig.initIdentityScope(type);

        recordBeanDaoConfig = daoConfigMap.get(RecordBeanDao.class).clone();
        recordBeanDaoConfig.initIdentityScope(type);

        smsEntityDaoConfig = daoConfigMap.get(SmsEntityDao.class).clone();
        smsEntityDaoConfig.initIdentityScope(type);

        lastOrderBeanDao = new LastOrderBeanDao(lastOrderBeanDaoConfig, this);
        recordBeanDao = new RecordBeanDao(recordBeanDaoConfig, this);
        smsEntityDao = new SmsEntityDao(smsEntityDaoConfig, this);

        registerDao(LastOrderBean.class, lastOrderBeanDao);
        registerDao(RecordBean.class, recordBeanDao);
        registerDao(SmsEntity.class, smsEntityDao);
    }
    
    public void clear() {
        lastOrderBeanDaoConfig.getIdentityScope().clear();
        recordBeanDaoConfig.getIdentityScope().clear();
        smsEntityDaoConfig.getIdentityScope().clear();
    }

    public LastOrderBeanDao getLastOrderBeanDao() {
        return lastOrderBeanDao;
    }

    public RecordBeanDao getRecordBeanDao() {
        return recordBeanDao;
    }

    public SmsEntityDao getSmsEntityDao() {
        return smsEntityDao;
    }

}
