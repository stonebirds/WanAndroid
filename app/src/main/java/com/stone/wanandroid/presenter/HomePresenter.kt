package com.stone.wanandroid.presenter

import com.stone.common.base.BasePresenter
import com.stone.wanandroid.net.bean.BaseResult
import com.stone.wanandroid.net.manager.RetrofitHelper
import com.stone.wanandroid.net.manager.RxHelper
import com.stone.wanandroid.net.manager.RxSubscribe
import com.stone.wanandroid.bean.HomeBannerBean
import com.stone.wanandroid.bean.ArticleBean
import com.stone.wanandroid.contract.HomeContract
import com.stone.wanandroid.net.config.ApiService

/**
 *
 * 作者：李飞磊
 * 描述：please add a description here
 * 时间：2019-07-03
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {
    override fun getHomeBanner() {
        RetrofitHelper.createService(ApiService::class.java)
            .getHomeBanner()
            .compose(RxHelper.handleScheduler())
            .subscribe(object : RxSubscribe<BaseResult<List<HomeBannerBean>>>() {
                override fun onSuccess(data: BaseResult<List<HomeBannerBean>>) {
                    if (data.errorCode == 0) {
                        val list = data.data
                        mView?.getHomeBannerSuccess(list)
                    } else {
                        mView?.getHomeBannerFailed(data.errorCode, data.errorMsg)
                    }
                }

                override fun onFailed(errorCode: Int, errorMsg: String) {
                    mView?.getHomeBannerFailed(errorCode, errorMsg)
                }
            })
    }

    override fun getHomeArticle(isRefresh: Boolean, page: Int) {
        RetrofitHelper.createService(ApiService::class.java)
            .getArticleList(page)
            .compose(RxHelper.handleScheduler())
            .subscribe(object : RxSubscribe<BaseResult<ArticleBean>>() {
                override fun onSuccess(data: BaseResult<ArticleBean>) {
                    val errorCode = data.errorCode
                    if (errorCode == 0) {
                        mView?.getHomeArticleSuccess(data.data)
                    } else {
                        mView?.getHomeArticleFailed(data.errorCode, data.errorMsg)
                    }
                }

                override fun onFailed(errorCode: Int, errorMsg: String) {
                    mView?.getHomeArticleFailed(errorCode, errorMsg)
                }

            })
    }

}