package com.stone.wanandroid.manager

import android.text.TextUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.stone.wanandroid.bean.LoginBean

/**
 *
 * 作者：李飞磊
 * 描述：用户信息的保存
 * 时间：2019-08-07
 */
class UserInfoManager {
    companion object {
        //存储用户信息
        const val USER_INFO: String = "USER_INFO"

        fun saveUserInfo(bean: LoginBean) {
            SPUtils.getInstance().put(USER_INFO, GsonUtils.toJson(bean))
        }

        fun getUserInfo(): LoginBean? {
            val json = SPUtils.getInstance().getString(USER_INFO)

            return GsonUtils.fromJson(json, LoginBean::class.javaObjectType)
        }

        fun deleteUserInfo() {
            SPUtils.getInstance().clear()
        }

        fun isLogin(): Boolean {
            return getUserInfo() != null
        }
    }
}