package com.android.smsutil.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.anye.greendao.gen.DaoMaster;
import com.anye.greendao.gen.LastOrderBeanDao;
import com.anye.greendao.gen.RecordBeanDao;
import com.anye.greendao.gen.SmsEntityDao;

public class SmsDaoMaster extends DaoMaster.DevOpenHelper {
    public SmsDaoMaster(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DbUpdateUtils.migrate(db, SmsEntityDao.class, LastOrderBeanDao.class, RecordBeanDao.class);
    }
}
