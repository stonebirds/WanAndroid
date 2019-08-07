package com.stone.wanandroid

import android.app.Application
import android.content.Context
import com.stone.wanandroid.net.manager.RetrofitHelper

/**
 * @name WanAndroid
 * @class name：com.stone.wanandroid
 * @class describe
 * @author 李飞磊
 * @time 2019/5/28 13:34
 * @change
 * @chang time
 * @class describe
 */
class WanAndroidApplication : Application() {

    companion object {
        var mContext: Application? = null
        fun getContext(): Context {
            return mContext!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

}