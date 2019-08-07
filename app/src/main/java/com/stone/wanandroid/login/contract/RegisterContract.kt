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
interface RegisterContract {
    interface View : IBaseView {
        fun registerSuccess(bean: LoginBean)
        fun registerFailed(errorCode: Int, errorMsg: String)
    }

    interface Presenter : IPresenter<View> {
        fun register(username: String, password: String)
    }
}