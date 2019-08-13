package com.stone.wanandroid.home.presenter

import com.stone.common.base.BasePresenter
import com.stone.wanandroid.category.bean.CategoryArticleBean
import com.stone.wanandroid.category.bean.CategoryBean
import com.stone.wanandroid.category.contract.CategoryContract
import com.stone.wanandroid.net.bean.BaseResult
import com.stone.wanandroid.net.config.ApiService
import com.stone.wanandroid.net.manager.RetrofitHelper
import com.stone.wanandroid.net.manager.RxHelper
import com.stone.wanandroid.net.manager.RxSubscribe

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-07-03
 */
class CategoryPresenter : BasePresenter<CategoryContract.View>(), CategoryContract.Presenter {
    override fun getCategoryList() {
        RetrofitHelper.createService(ApiService::class.java)
            .getCategoryList()
            .compose(RxHelper.handleScheduler())
            .subscribe(object : RxSubscribe<BaseResult<List<CategoryBean>>>() {
                override fun onSuccess(data: BaseResult<List<CategoryBean>>) {
                    if (data.errorCode == 0) {
                        mView?.getCategoryListSuccess(data.data)
                    } else {
                        mView?.getCategoryListFailed(data.errorCode, data.errorMsg)
                    }
                }

                override fun onFailed(errorCode: Int, errorMsg: String) {
                    mView?.getCategoryListFailed(errorCode, errorMsg)
                }
            })
    }

    override fun getCategoryArticleList(page: Int,cid: String) {
        RetrofitHelper.createService(ApiService::class.java)
            .getCategoryArticleList(page, cid)
            .compose(RxHelper.handleScheduler())
            .subscribe(object : RxSubscribe<BaseResult<CategoryArticleBean>>() {
                override fun onSuccess(data: BaseResult<CategoryArticleBean>) {
                    if (data.errorCode == 0) {
                        mView?.getCategoryArticleListSuccess(data.data)
                    } else {
                        mView?.getCategoryArticleListFailed(data.errorCode, data.errorMsg)
                    }
                }

                override fun onFailed(errorCode: Int, errorMsg: String) {
                    mView?.getCategoryArticleListFailed(errorCode, errorMsg)
                }
            })
    }
}