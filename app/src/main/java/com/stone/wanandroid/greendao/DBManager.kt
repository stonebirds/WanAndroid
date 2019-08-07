package com.stone.wanandroid.greendao

import android.content.Context
import android.database.sqlite.SQLiteDatabase

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-07
 */
class DBManager(context: Context) {
    private val DB_NAME = "stone.db"
    private var mDevOpenHelper: DaoMaster.DevOpenHelper? = null
    private var mDaoMaster: DaoMaster? = null
    private var mDaoSession: DaoSession? = null

    init {
        mDevOpenHelper = DaoMaster.DevOpenHelper(context, DB_NAME)
        getDaoMaster(context)
        getDaoSession(context)
    }

    companion object {
        @Volatile
        var instance: DBManager? = null

        fun getInstance(context: Context): DBManager? {
            if (instance == null) {
                synchronized(DBManager::class.java) {
                    if (instance == null) {
                        instance = DBManager(context)
                    }
                }
            }

            return instance
        }
    }

    fun getReadableDatabase(context: Context): SQLiteDatabase? {
        if (null == mDevOpenHelper) {
            getInstance(context)
        }

        return mDevOpenHelper?.readableDatabase
    }

    fun getWritableDatabase(context: Context): SQLiteDatabase? {
        if (null == mDevOpenHelper) {
            getInstance(context)
        }

        return mDevOpenHelper?.writableDatabase
    }

    fun getDaoMaster(context: Context): DaoMaster? {
        if (null == mDaoMaster) {
            synchronized(DBManager::class.java) {
                if (null == mDaoMaster) {
                    mDaoMaster = DaoMaster(getWritableDatabase(context))
                }
            }
        }

        return mDaoMaster
    }

    fun getDaoSession(context: Context): DaoSession? {
        if (null == mDaoSession) {
            synchronized(DBManager::class.java) {
                if (null == mDaoSession) {
                    mDaoSession = getDaoMaster(context)?.newSession()
                }
            }
        }

        return mDaoSession
    }
}