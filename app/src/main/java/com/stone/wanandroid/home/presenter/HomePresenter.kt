package com.stone.wanandroid.home.presenter

import com.blankj.utilcode.util.LogUtils
import com.stone.common.base.BasePresenter
import com.stone.net.bean.BaseResult
import com.stone.net.manager.RetrofitHelper
import com.stone.net.manager.RxHelper
import com.stone.net.manager.RxSubscribe
import com.stone.wanandroid.home.bean.HomeBannerBean
import com.stone.wanandroid.home.bean.HomeBean
import com.stone.wanandroid.home.contract.HomeContract
import com.stone.wanandroid.net.ApiService

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
                    if (data.errorCode == 0){
                        val list = data.data
                        mView?.getHomeBannerSuccess(list)
                    }else{
                        mView?.getHomeBannerFailed(data.errorCode, data.errorMsg)
                    }
                }

                override fun onFailed(errorCode: Int, errorMsg: String) {

                }
            })
    }

    override fun getHomeArticle(isRefresh : Boolean, page: Int) {
        RetrofitHelper.createService(ApiService::class.java)
            .getArticleList(page)
            .compose(RxHelper.handleScheduler())
            .subscribe(object : RxSubscribe<BaseResult<HomeBean>>(){
                override fun onSuccess(data: BaseResult<HomeBean>) {
                    val errorCode = data.errorCode
                    if (errorCode == 0) {
                        mView?.getHomeArticleSuccess(data.data)
                    }else{
                        mView?.getHomeArticleFailed(data.errorCode, data.errorMsg)
                    }
                }

                override fun onFailed(errorCode: Int, errorMsg: String) {
                }

            })
    }

}