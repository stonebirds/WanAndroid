package com.stone.wanandroid.greendao

import android.content.Context
import com.stone.wanandroid.bean.UserInfoBean

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-07
 */
class UserInfoHelper {
    private object mHolder {
        val instance = UserInfoHelper()
    }

    companion object{
        fun getIstance():UserInfoHelper{
            return mHolder.instance
        }
    }

    fun saveUserInfo(context: Context,userInfoBean: UserInfoBean){
        DBManager.getInstance(context)?.getDaoSession(context)?.userInfoBeanDao?.save(userInfoBean)
    }

    fun getUserInfo(context: Context):MutableList<UserInfoBean>?{
        return DBManager.getInstance(context)?.getDaoSession(context)?.userInfoBeanDao?.queryBuilder()?.build()?.list()
    }
}