package com.stone.wanandroid.home.contract

import com.stone.common.base.IBaseView
import com.stone.common.base.IPresenter
import com.stone.wanandroid.home.bean.HomeBannerBean

interface HomeContract {
    interface View : IBaseView {
        fun getHomeBannerSuccess(date: List<HomeBannerBean>)

        fun getHomeBannerFailed(errCode:Int,message:String)

//        fun getHomeArticleSuccess()
//
//        fun getHomeArticleFailed()
    }

    interface Presenter : IPresenter<View> {
        fun getHomeBanner()

//        fun getHomeArticle()
    }
}
