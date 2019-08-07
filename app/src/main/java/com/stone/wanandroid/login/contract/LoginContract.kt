package com.stone.wanandroid.login.contract

import com.stone.common.base.IBaseView
import com.stone.common.base.IPresenter
import com.stone.wanandroid.login.bean.LoginBean

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-06
 */
interface LoginContract {
    interface View : IBaseView {
        fun loginSuccess(bean: LoginBean)
        fun loginFailed(errorCode: Int, errorMsg: String)
    }

    interface Presenter : IPresenter<View> {
        fun login(username: String, password: String)
    }
}