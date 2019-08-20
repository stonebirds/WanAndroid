package com.stone.wanandroid.contract

import com.stone.common.base.IBaseView
import com.stone.common.base.IPresenter

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-13
 */
interface SettingContract {
    interface View : IBaseView {
        fun doLogoutSuccess()

        fun doLogoutFailed(errorCode: Int, message: String)
    }

    interface Presenter : IPresenter<View> {
        fun doLogout()
    }
}