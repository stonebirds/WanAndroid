package com.stone.wanandroid.presenter

import com.stone.common.base.BasePresenter
import com.stone.wanandroid.bean.LoginBean
import com.stone.wanandroid.contract.LoginContract
import com.stone.wanandroid.manager.UserInfoManager
import com.stone.wanandroid.net.bean.BaseResult
import com.stone.wanandroid.net.config.ApiService
import com.stone.wanandroid.net.manager.RetrofitHelper
import com.stone.wanandroid.net.manager.RxHelper
import com.stone.wanandroid.net.manager.RxSubscribe

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-08-06
 */
class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {
    override fun login(username: String, password: String) {
        RetrofitHelper.createService(ApiService::class.java)
            .doLogin(username, password)
            .compose(RxHelper.handleScheduler())
            .subscribe(object : RxSubscribe<BaseResult<LoginBean>>() {
                override fun onSuccess(data: BaseResult<LoginBean>) {
                    if (data.errorCode == 0) {
                        //保存用户信息
                        UserInfoManager.saveUserInfo(data.data)
                        mView?.loginSuccess(data.data)
                    } else {
                        mView?.loginFailed(data.errorCode, data.errorMsg)
                    }
                }

                override fun onFailed(errorCode: Int, errorMsg: String) {
                    mView?.loginFailed(errorCode, errorMsg)
                }

            })
    }
}