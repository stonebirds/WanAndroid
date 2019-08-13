package com.stone.wanandroid.presenter

import com.stone.common.base.BasePresenter
import com.stone.wanandroid.net.bean.BaseResult
import com.stone.wanandroid.net.manager.RetrofitHelper
import com.stone.wanandroid.net.manager.RxHelper
import com.stone.wanandroid.net.manager.RxSubscribe
import com.stone.wanandroid.bean.ArticleBean
import com.stone.wanandroid.net.config.ApiService
import com.stone.wanandroid.contract.ProjectContract

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-07-03
 */
class ProjectPresenter : BasePresenter<ProjectContract.View>(), ProjectContract.Presenter {
    override fun getProjectList(isRefresh: Boolean, page: Int) {
        RetrofitHelper.createService(ApiService::class.java)
            .getProjectList(page)
            .compose(RxHelper.handleScheduler())
            .subscribe(object : RxSubscribe<BaseResult<ArticleBean>>() {
                override fun onSuccess(data: BaseResult<ArticleBean>) {
                    val errorCode = data.errorCode
                    if (errorCode == 0) {
                        mView?.getProjectListSuccess(data.data)
                    } else {
                        mView?.getProjectListFailed(data.errorCode, data.errorMsg)
                    }
                }

                override fun onFailed(errorCode: Int, errorMsg: String) {
                    mView?.getProjectListFailed(errorCode, errorMsg)
                }

            })
    }

}