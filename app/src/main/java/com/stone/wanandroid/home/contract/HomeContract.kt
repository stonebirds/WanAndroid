package com.stone.wanandroid.home.contract

import com.stone.common.base.IBaseView
import com.stone.common.base.IPresenter
import com.stone.wanandroid.home.bean.HomeBannerBean
import com.stone.wanandroid.home.bean.HomeBean

interface HomeContract {
    interface View : IBaseView {
        fun getHomeBannerSuccess(date: List<HomeBannerBean>)

        fun getHomeBannerFailed(errCode: Int, message: String)

        fun getHomeArticleSuccess(bean: HomeBean)

        fun getHomeArticleFailed(errCode: Int, message: String)
    }

    interface Presenter : IPresenter<View> {
        fun getHomeBanner()

        fun getHomeArticle(isRefresh: Boolean, page: Int)
    }
}
