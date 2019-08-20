package com.stone.wanandroid.presenter

import com.stone.common.base.BasePresenter
import com.stone.wanandroid.bean.LogoutBean
import com.stone.wanandroid.contract.SettingContract
import com.stone.wanandroid.net.bean.BaseResult
import com.stone.wanandroid.net.config.ApiService
import com.stone.wanandroid.net.manager.RetrofitHelper
import com.stone.wanandroid.net.manager.RxHelper
import com.stone.wanandroid.net.manager.RxSubscribe

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-13
 */
class SettingPresenter : BasePresenter<SettingContract.View>(), SettingContract.Presenter {
    override fun doLogout() {
        RetrofitHelper.createService(ApiService::class.java)
            .doLogout()
            .compose(RxHelper.handleScheduler())
            .subscribe(object : RxSubscribe<BaseResult<LogoutBean>>() {
                override fun onSuccess(data: BaseResult<LogoutBean>) {
                    if (data.errorCode == 0) {
                        //保存用户信息
                        mView?.doLogoutSuccess()
                    } else {
                        mView?.doLogoutFailed(data.errorCode, data.errorMsg)
                    }
                }

                override fun onFailed(errorCode: Int, errorMsg: String) {
                    mView?.doLogoutFailed(errorCode, errorMsg)
                }

            })
    }
}